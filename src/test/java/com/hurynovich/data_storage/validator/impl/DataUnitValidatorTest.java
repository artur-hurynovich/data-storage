package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.BaseService;
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

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitValidatorTest {

	@Mock
	private BaseService<DataUnitSchemaServiceModel, Long> dataUnitSchemaService;

	@Mock
	private DataUnitPropertyValueCheckProcessor checkProcessor;

	private Validator<DataUnitApiModel> validator;

	private final ModelGenerator<DataUnitApiModel> dataUnitApiModelGenerator =
			new DataUnitApiModelGenerator();

	private final ModelGenerator<DataUnitSchemaServiceModel> dataUnitSchemaServiceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitValidator(dataUnitSchemaService, checkProcessor);
	}

	@Test
	void validateTest() {
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
				Mockito.any(Object.class))).thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	@Test
	void validateSchemaIdIsNullTest() {
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		TestReflectionUtils.setField(dataUnit, DataUnitServiceModelImpl_.SCHEMA_ID, null);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.schemaId' can't be null", errors.iterator().next());
	}

	@Test
	void validateSchemaEmptyTest() {
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		TestReflectionUtils.setField(dataUnit, DataUnitServiceModelImpl_.SCHEMA_ID, INCORRECT_LONG_ID);
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
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		TestReflectionUtils.setField(dataUnit, DataUnitServiceModelImpl_.PROPERTIES, null);
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
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
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		TestReflectionUtils.setField(dataUnit, DataUnitServiceModelImpl_.PROPERTIES, new ArrayList<>());
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
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
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		final List<DataUnitPropertyApiModel> properties = new ArrayList<>(dataUnit.getProperties());
		properties.add(null);
		TestReflectionUtils.setField(dataUnit, DataUnitServiceModelImpl_.PROPERTIES, properties);

		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
				Mockito.any(Object.class))).thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.property' can't be null", errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIdIsNullTest() {
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		TestReflectionUtils.setField(dataUnit.getProperties().get(0), DataUnitServiceModelImpl_.SCHEMA_ID, null);
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
				Mockito.any(Object.class))).thenReturn(true);

		final ValidationResult validationResult = validator.validate(dataUnit);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnit.property.schemaId' can't be null", errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIdDuplicateTest() {
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		final Long schemaId = dataUnit.getProperties().get(1).getSchemaId();
		TestReflectionUtils.setField(dataUnit.getProperties().get(0), DataUnitServiceModelImpl_.SCHEMA_ID, schemaId);
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
				Mockito.any(Object.class))).thenReturn(true);

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
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		final List<DataUnitPropertySchemaServiceModel> propertySchemas = dataUnitSchema.getPropertySchemas().stream().
				filter(propertySchema -> !propertySchema.getId().equals(DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID)).
				collect(Collectors.toList());
		TestReflectionUtils.setField(dataUnitSchema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
				Mockito.any(Object.class))).thenReturn(true);

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
		final DataUnitApiModel dataUnit = dataUnitApiModelGenerator.generate();
		final DataUnitSchemaServiceModel dataUnitSchema = dataUnitSchemaServiceModelGenerator.generate();
		Mockito.when(dataUnitSchemaService.findById(dataUnit.getSchemaId())).thenReturn(Optional.of(dataUnitSchema));
		Mockito.when(checkProcessor.processCheck(Mockito.any(DataUnitPropertySchemaServiceModel.class),
						Mockito.any(Object.class))).
				thenAnswer(invocation -> {
					final boolean result;

					final DataUnitPropertySchemaServiceModel propertySchema = invocation.
							getArgument(0, DataUnitPropertySchemaServiceModel.class);
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
