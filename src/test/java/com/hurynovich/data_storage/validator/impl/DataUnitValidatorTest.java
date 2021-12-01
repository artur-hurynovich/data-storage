package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.BaseService;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitValidatorTest {

	@Mock
	private BaseService<DataUnitSchemaDTO, Long> dataUnitSchemaService;

	@Mock
	private DataUnitPropertyValueCheckProcessor checkProcessor;

	private Validator<DataUnitDTO> validator;

	private final TestIdentifiedObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> dataUnitSchemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitValidator(dataUnitSchemaService, checkProcessor);
	}

	@Test
	void validateTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	@Test
	void validateSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.SCHEMA_ID, null);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.schemaId' can't be null", errors.iterator().next());
	}

	@Test
	void validateSchemaEmptyTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.SCHEMA_ID, INCORRECT_LONG_ID);
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.empty());

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found",
				errors.iterator().next());
	}

	@Test
	void validatePropertiesIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, null);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.properties' can't be null or empty", errors.iterator().next());
	}

	@Test
	void validatePropertiesIsEmptyTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, new ArrayList<>());
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.properties' can't be null or empty", errors.iterator().next());
	}

	@Test
	void validatePropertyIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final List<DataUnitDTO.DataUnitPropertyDTO> properties = new ArrayList<>(dataUnit.getProperties());
		properties.add(null);
		TestReflectionUtils.setField(dataUnit, DataUnitDocument_.PROPERTIES, properties);

		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.property' can't be null", errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		TestReflectionUtils.setField(dataUnit.getProperties().get(0), DataUnitDocument_.SCHEMA_ID, null);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.property.schemaId' can't be null", errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIdDuplicateTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final Long schemaId = dataUnit.getProperties().get(1).getSchemaId();
		TestReflectionUtils.setField(dataUnit.getProperties().get(0), DataUnitDocument_.SCHEMA_ID, schemaId);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + schemaId + "' for 'dataUnit.property.schemaId'",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		final List<DataUnitPropertySchemaDTO> propertySchemas = dataUnitSchema.getPropertySchemas().stream().
				filter(propertySchema -> !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID)).
				collect(Collectors.toList());
		TestReflectionUtils.setField(dataUnitSchema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, propertySchemas);
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitPropertySchema' with id = '" + DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID +
				"' not found", errors.iterator().next());
	}

	@Test
	void validateIncorrectPropertyValueTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenAnswer(invocation -> {
					final boolean result;

					final DataUnitPropertySchemaDTO propertySchema = invocation.
							getArgument(0, DataUnitPropertySchemaDTO.class);
					if (propertySchema != null) {
						result = !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID);
					} else {
						result = false;
					}

					return result;
				});

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.property.value' '" + DATA_UNIT_TEXT_PROPERTY_VALUE +
						"' is incorrect for dataUnitProperty with schemaId = " + DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID,
				errors.iterator().next());
	}
}
