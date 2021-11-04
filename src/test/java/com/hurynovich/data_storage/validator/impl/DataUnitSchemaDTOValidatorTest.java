package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.RandomStringUtils;
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
class DataUnitSchemaDTOValidatorTest {

	@Mock
	private DataUnitSchemaDTOService service;

	private DTOValidator<DataUnitSchemaDTO> validator;

	private final TestObjectGenerator<DataUnitSchemaDTO> schemaGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitSchemaDTOValidator(new DTOValidationHelperImpl(), service);
	}

	@Test
	void validateTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		TestReflectionUtils.setField(schema, "name", schemaName);

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
		processValidateSchemaNameTest("");
	}

	@Test
	void validateSchemaNameIsBlankTest() {
		processValidateSchemaNameTest(" ");
	}

	@Test
	void validateSchemaNameExceedsMaxLengthTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		TestReflectionUtils.setField(schema, "name", RandomStringUtils.randomAlphabetic(26));

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		TestReflectionUtils.setField(schema, "id", null);
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		TestReflectionUtils.setField(schema, "propertySchemas", null);
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
		final DataUnitSchemaDTO dataUnitSchema = schemaGenerator.generateSingleObject();
		TestReflectionUtils.setField(dataUnitSchema, "propertySchemas", new ArrayList<>());
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		schema.getPropertySchemas().set(0, null);
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final DataUnitSchemaDTO existingSchema = schemaGenerator.generateSingleObject();
		final List<DataUnitPropertySchemaDTO> existingPropertySchemas = existingSchema.getPropertySchemas();
		TestReflectionUtils.setField(existingSchema, "propertySchemas",
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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				"name", propertySchemaName);
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

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
		processValidatePropertySchemaNameTest("");
	}

	@Test
	void validatePropertySchemaNameIsBlankTest() {
		processValidatePropertySchemaNameTest(" ");
	}

	@Test
	void validatePropertySchemaNameExceedsMaxLengthTest() {
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				"name", RandomStringUtils.randomAlphabetic(26));
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		final String name = schema.getPropertySchemas().get(0).getName();
		TestReflectionUtils.setField(schema.getPropertySchemas().get(1),
				"name", name);
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

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
		final DataUnitSchemaDTO schema = schemaGenerator.generateSingleObject();
		schema.getPropertySchemas().forEach(propertySchema ->
				TestReflectionUtils.setField(propertySchema, "id", null));
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				"type", null);
		Mockito.when(service.existsByNameAndNotId(schema.getName(), schema.getId())).thenReturn(false);

		final ValidationResult validationResult = validator.validate(schema);
		Assertions.assertNotNull(validationResult);
		Assertions.assertEquals(ValidationResultType.FAILURE, validationResult.getType());

		final Set<String> errors = validationResult.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.type' can't be null",
				errors.iterator().next());
	}

}
