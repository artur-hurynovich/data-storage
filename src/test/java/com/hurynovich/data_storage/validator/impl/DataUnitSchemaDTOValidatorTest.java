package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
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
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class DataUnitSchemaDTOValidatorTest {

	@Mock
	private DataUnitSchemaDTOService service;

	private DTOValidator<DataUnitSchemaDTO> validator;

	private final TestObjectGenerator<DataUnitSchemaDTO> testObjectGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@BeforeEach
	public void initValidator() {
		validator = new DataUnitSchemaDTOValidator(service);
	}

	@Test
	void validateTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.SUCCESS, result.getType());
		Assertions.assertTrue(result.getErrors().isEmpty());
	}

	@Test
	void validateSchemaIsNullTest() {
		final ValidationResult result = validator.validate(null);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema' can't be null"));
	}

	@Test
	void validateSchemaNameIsNullTest() {
		processValidateSchemaNameTest(null);
	}

	private void processValidateSchemaNameTest(final String schemaName) {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setName(schemaName);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' can't be null, empty or blank"));
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
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setName(RandomStringUtils.randomAlphabetic(26));
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' can't exceed 25 characters"));
	}

	@Test
	void validateSchemaNameExistsIdIsNull() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setId(null);
		final String name = dataUnitSchema.getName();
		Mockito.when(service.existsByName(name)).thenReturn(true);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' found duplicate '" +
				name + "'"));
	}

	@Test
	void validateSchemaNameExistsIdIsNotNull() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		final String name = dataUnitSchema.getName();
		Mockito.when(service.existsByNameAndNotId(name, dataUnitSchema.getId())).thenReturn(true);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' found duplicate '" +
				name + "'"));
	}

	@Test
	void validatePropertySchemasIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setPropertySchemas(null);
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchemas' can't be null or empty"));
	}

	@Test
	void validatePropertySchemasIsEmptyTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setPropertySchemas(new ArrayList<>());
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchemas' can't be null or empty"));
	}

	@Test
	void validatePropertySchemaIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().set(0, null);
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema' can't be null"));
	}

	@Test
	void validatePropertySchemaNameIsNullTest() {
		processValidatePropertySchemaNameTest(null);
	}

	private void processValidatePropertySchemaNameTest(final String propertySchemaName) {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName(propertySchemaName);
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't be null, empty or blank"));
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
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName(
				RandomStringUtils.randomAlphabetic(26));
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't exceed 25 characters"));
	}

	@Test
	void validatePropertySchemaNameDuplicateTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		final String name = dataUnitSchema.getPropertySchemas().get(0).getName();
		dataUnitSchema.getPropertySchemas().get(1).setName(name);
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' found duplicate '" +
				name + "'"));
	}

	@Test
	void validatePropertySchemaTypeIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setType(null);
		Mockito.when(service.existsByNameAndNotId(dataUnitSchema.getName(), dataUnitSchema.getId())).thenReturn(false);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.type' can't be null"));
	}

}
