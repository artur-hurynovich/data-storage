package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.test_object_generator.TestObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

class DataUnitSchemaDTOValidatorTest {

	private final DTOValidator<DataUnitSchemaDTO> validator = new DataUnitSchemaDTOValidator();

	private final TestObjectGenerator<DataUnitSchemaDTO> testObjectGenerator =
			new TestDataUnitSchemaDTOGenerator();

	@Test
	void validateTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
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
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setName(null);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' can't be null, empty or blank"));
	}

	@Test
	void validateSchemaNameIsEmptyTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setName("");
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' can't be null, empty or blank"));
	}

	@Test
	void validateSchemaNameIsBlankTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setName(" ");
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.name' can't be null, empty or blank"));
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
	void validatePropertySchemasIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.setPropertySchemas(null);
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
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema' can't be null"));
	}

	@Test
	void validatePropertySchemaNameIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName(null);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't be null, empty or blank"));
	}

	@Test
	void validatePropertySchemaNameIsEmptyTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName("");
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't be null, empty or blank"));
	}

	@Test
	void validatePropertySchemaNameIsBlankTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName(" ");
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't be null, empty or blank"));
	}

	@Test
	void validatePropertySchemaNameExceedsMaxLengthTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setName(RandomStringUtils.randomAlphabetic(26));
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.name' can't exceed 25 characters"));
	}

	@Test
	void validatePropertySchemaTypeIsNullTest() {
		final DataUnitSchemaDTO dataUnitSchema = testObjectGenerator.generateSingleObject();
		dataUnitSchema.getPropertySchemas().iterator().next().setType(null);
		final ValidationResult result = validator.validate(dataUnitSchema);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ValidationResultType.FAILURE, result.getType());

		final Set<String> errors = result.getErrors();
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.propertySchema.type' can't be null"));
	}

}
