package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaValidatorTest {

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	@Mock
	private DataUnitSchemaService service;

	private Validator<DataUnitSchemaApiModel> validator;

	private final ModelGenerator<DataUnitSchemaApiModel> apiModelGenerator =
			new DataUnitSchemaApiModelGenerator();

	private final ModelGenerator<DataUnitSchemaServiceModel> serviceModelGenerator =
			new DataUnitSchemaServiceModelGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitSchemaValidator(service);
	}

	@Test
	void validateTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.SUCCESS, validationResult.getType());
		Assertions.assertTrue(validationResult.getErrors().isEmpty());
	}

	@Test
	void validateSchemaNameIsNullTest() {
		processValidateSchemaNameTest(null);
	}

	private void processValidateSchemaNameTest(final String schemaName) {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, schemaName);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.name' can't be null, empty or blank",
				errors.iterator().next());
	}

	@Test
	void validateSchemaNameIsEmptyTest() {
		processValidateSchemaNameTest(StringUtils.EMPTY);
	}

	@Test
	void validateSchemaNameIsBlankTest() {
		processValidateSchemaNameTest(StringUtils.SPACE);
	}

	@Test
	void validateSchemaNameExceedsMaxLengthTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + 1));

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.name' can't exceed " +
						DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + " characters",
				errors.iterator().next());
	}

	@Test
	void validateSchemaNameExistsIdIsNull() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		final String name = schema.getName();
		Mockito.when(service.existsByName(name)).thenReturn(true);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + name + "' for 'dataUnitSchema.name'",
				errors.iterator().next());
	}

	@Test
	void validateSchemaNameExistsIdIsNotNull() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generate();
		Mockito.when(service.findById(schema.getId())).
				thenReturn(Optional.of(serviceModelGenerator.generate()));
		final String name = schema.getName();
		Mockito.when(service.existsByNameAndNotId(name, schema.getId())).thenReturn(true);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + name + "' for 'dataUnitSchema.name'",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemasIsNullTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generate();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, null);
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchemas' can't be null or empty",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemasIsEmptyTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, new ArrayList<>());
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchemas' can't be null or empty",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIsNullTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generate();
		final List<DataUnitPropertySchemaApiModel> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		propertySchemas.add(null);
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		Mockito.when(service.findById(schema.getId())).
				thenReturn(Optional.of(serviceModelGenerator.generate()));
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema' can't be null",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNotFoundTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generate();
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final DataUnitSchemaServiceModel existingSchema = serviceModelGenerator.generate();
		final List<DataUnitPropertySchemaServiceModel> existingPropertySchemas = existingSchema.getPropertySchemas();
		TestReflectionUtils.setField(existingSchema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS,
				existingPropertySchemas.subList(1, existingPropertySchemas.size()));
		Mockito.when(service.findById(existingSchema.getId())).
				thenReturn(Optional.of(existingSchema));

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitPropertySchema' with id = '" + DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID +
				"' not found", errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNameIsNullTest() {
		processValidatePropertySchemaNameTest(null);
	}

	private void processValidatePropertySchemaNameTest(final String propertySchemaName) {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitSchemaServiceModelImpl_.NAME, propertySchemaName);
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.name' can't be null, empty or blank",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNameIsEmptyTest() {
		processValidatePropertySchemaNameTest(StringUtils.EMPTY);
	}

	@Test
	void validatePropertySchemaNameIsBlankTest() {
		processValidatePropertySchemaNameTest(StringUtils.SPACE);
	}

	@Test
	void validatePropertySchemaNameExceedsMaxLengthTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitSchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + 1));
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.name' can't exceed " +
						DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + " characters",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNameDuplicateTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		final String name = schema.getPropertySchemas().get(0).getName();
		TestReflectionUtils.setField(schema.getPropertySchemas().get(1),
				DataUnitSchemaServiceModelImpl_.NAME, name);
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + name + "' for 'dataUnitSchema.propertySchema.name'",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaTypeIsNullTest() {
		final DataUnitSchemaApiModel schema = apiModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.TYPE, null);
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.type' can't be null",
				errors.iterator().next());
	}
}
