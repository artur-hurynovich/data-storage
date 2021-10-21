package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.validator.DTOValidator;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitDTOValidatorTest {

	@Mock
	private DTOService<DataUnitSchemaDTO, Long> dataUnitSchemaService;

	@Mock
	private DataUnitPropertyValueCheckProcessor checkProcessor;

	private DTOValidator<DataUnitDTO> validator;

	private final TestObjectGenerator<DataUnitDTO> dataUnitGenerator =
			new TestDataUnitDTOGenerator();

	private final TestObjectGenerator<DataUnitSchemaDTO> dataUnitSchemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitDTOValidator(dataUnitSchemaService, checkProcessor);
	}

	@Test
	void validateTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	@Test
	void validateDataUnitIsNullTest() {
		final ValidationResult validationResult = validator.validate(null);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit' can't be null", errors.iterator().next());
	}

	@Test
	void validateSchemaIdIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setSchemaId(null);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.schemaId' can't be null", errors.iterator().next());
	}

	@Test
	void validateSchemaEmptyTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setSchemaId(INCORRECT_LONG_ID);
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.empty());

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema' with id = " + INCORRECT_LONG_ID + " not found",
				errors.iterator().next());
	}

	@Test
	void validatePropertiesIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setProperties(null);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
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
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.setProperties(new ArrayList<>());
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
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
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.getProperties().set(0, null);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
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
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		dataUnit.getProperties().get(0).setSchemaId(null);
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
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
	void validatePropertySchemaIsNullTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
		dataUnitSchema.setPropertySchemas(dataUnitSchema.getPropertySchemas().stream().
				filter(propertySchema -> !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID)).
				collect(Collectors.toList()));
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaDTO.class), Mockito.any(Object.class))).
				thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitPropertySchema' with id = " + DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID +
				" not found", errors.iterator().next());
	}

	@Test
	void validateIncorrectPropertyValueTest() {
		final DataUnitDTO dataUnit = dataUnitGenerator.generateSingleObject();
		final DataUnitSchemaDTO dataUnitSchema = dataUnitSchemaGenerator.generateSingleObject();
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
