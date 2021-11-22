package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
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

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TEXT_PROPERTY_SCHEMA_ID;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaValidatorTest {

	@Mock
	private DataUnitSchemaService service;

	private Validator<DataUnitSchemaDTO> validator;

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitSchemaValidator(new ValidationHelperImpl(), service);
	}

	@Test
	void validateTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME, schemaName);

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME, RandomStringUtils.randomAlphabetic(26));

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.name' can't exceed 25 characters",
				errors.iterator().next());
	}

	@Test
	void validateSchemaNameExistsIdIsNull() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		Mockito.when(service.findById(schema.getId())).thenReturn(Optional.of(schema));
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, null);
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
		final DataUnitSchemaDTO dataUnitSchema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(dataUnitSchema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, new ArrayList<>());
		Mockito.when(service.existsByName(dataUnitSchema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchemas' can't be null or empty",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaIsNullTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		final List<DataUnitPropertySchemaDTO> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		propertySchemas.add(null);
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, propertySchemas);
		Mockito.when(service.findById(schema.getId())).thenReturn(Optional.of(schema));
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObject();
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final DataUnitSchemaDTO existingSchema = schemaGenerator.generateObject();
		final List<DataUnitPropertySchemaDTO> existingPropertySchemas = existingSchema.getPropertySchemas();
		TestReflectionUtils.setField(existingSchema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS,
				existingPropertySchemas.subList(1, existingPropertySchemas.size()));
		Mockito.when(service.findById(existingSchema.getId())).thenReturn(Optional.of(existingSchema));

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitSchemaEntity_.NAME, propertySchemaName);
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitSchemaEntity_.NAME, RandomStringUtils.randomAlphabetic(26));
		Mockito.when(service.existsByName(schema.getName())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.name' can't exceed 25 characters",
				errors.iterator().next());
	}

	@Test
	void validatePropertySchemaNameDuplicateTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		final String name = schema.getPropertySchemas().get(0).getName();
		TestReflectionUtils.setField(schema.getPropertySchemas().get(1),
				DataUnitSchemaEntity_.NAME, name);
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaEntity_.TYPE, null);
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
