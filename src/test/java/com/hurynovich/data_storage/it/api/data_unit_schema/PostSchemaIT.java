package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class PostSchemaIT extends AbstractDataUnitSchemaIT {

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	@Test
	void postSchemaTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		final ResponseEntity<DataUnitSchemaServiceModel> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		final DataUnitSchemaServiceModel responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		final Long responseSchemaId = responseSchema.getId();
		Assertions.assertNotNull(responseSchemaId);
		schemaAsserter.assertEquals(schema, responseSchema, AbstractServiceModel_.ID);

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(responseSchemaId);
		Assertions.assertNotNull(savedSchema);
		schemaAsserter.assertEquals(savedSchema, responseSchema);

		testDAO.deleteById(savedSchema.getId());
	}

	@Test
	void postSchemaNotNullIdTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generate();
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be null"));

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(schema.getId());
		Assertions.assertNull(savedSchema);
	}

	@Test
	void postSchemaNullNameTest() {
		processPostSchemaNotValidNameTest(null);
	}

	@Test
	void postSchemaEmptyNameTest() {
		processPostSchemaNotValidNameTest(StringUtils.EMPTY);
	}

	@Test
	void postSchemaBlankNameTest() {
		processPostSchemaNotValidNameTest(StringUtils.SPACE);
	}

	private void processPostSchemaNotValidNameTest(final String name) {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, name);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.name' can't be null, empty or blank",
				errors.iterator().next());
	}

	@Test
	void postSchemaNameMaxLengthTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + 1));
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.name' can't exceed " +
						DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + " characters",
				errors.iterator().next());
	}

	@Test
	void postSchemaNameDuplicateTest() {
		final DataUnitSchemaPersistentModel savedSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final String savedSchemaName = savedSchema.getName();

		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, savedSchemaName);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + savedSchemaName + "' for 'dataUnitSchema.name'",
				errors.iterator().next());

		testDAO.deleteById(savedSchema.getId());
	}

	@Test
	void processPostSchemaNullPropertySchemasTest() {
		processPostSchemaNotValidPropertySchemasTest(null);
	}

	@Test
	void processPostSchemaEmptyPropertySchemasTest() {
		processPostSchemaNotValidPropertySchemasTest(new ArrayList<>());
	}

	private void processPostSchemaNotValidPropertySchemasTest(
			final List<DataUnitPropertySchemaServiceModel> propertySchemas) {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchemas' can't be null or empty",
				errors.iterator().next());
	}

	@Test
	void postSchemaNullPropertySchemaTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		final List<DataUnitPropertySchemaServiceModel> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		propertySchemas.add(null);
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema' can't be null",
				errors.iterator().next());
	}

	@Test
	void postSchemaNotNullPropertySchemaIdTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		final List<DataUnitPropertySchemaServiceModel> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		final Long id = serviceModelGenerator.generate().getPropertySchemas().
				iterator().next().getId();
		TestReflectionUtils.setField(propertySchemas.iterator().next(), AbstractServiceModel_.ID, id);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitPropertySchema' with id = '" + id + "' not found",
				errors.iterator().next());
	}

	@Test
	void postSchemaNullPropertySchemaNameTest() {
		processPostSchemaNotValidPropertySchemaNameTest(null);
	}

	@Test
	void postSchemaEmptyPropertySchemaNameTest() {
		processPostSchemaNotValidPropertySchemaNameTest(StringUtils.EMPTY);
	}

	@Test
	void postSchemaBlankPropertySchemaNameTest() {
		processPostSchemaNotValidPropertySchemaNameTest(StringUtils.SPACE);
	}

	private void processPostSchemaNotValidPropertySchemaNameTest(final String name) {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.NAME, name);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.name' can't be null, empty or blank",
				errors.iterator().next());
	}

	@Test
	void postSchemaPropertySchemaNameMaxLengthTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + 1));
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.name' can't exceed " +
						DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + " characters",
				errors.iterator().next());
	}

	@Test
	void postSchemaPropertySchemaNameDuplicateTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		final List<DataUnitPropertySchemaServiceModel> propertySchemas = schema.getPropertySchemas();
		final String duplicateName = propertySchemas.get(0).getName();
		TestReflectionUtils.setField(propertySchemas.get(1),
				DataUnitPropertySchemaServiceModelImpl_.NAME, duplicateName);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + duplicateName +
						"' for 'dataUnitSchema.propertySchema.name'",
				errors.iterator().next());
	}

	@Test
	void postSchemaNullPropertySchemaTypeTest() {
		final DataUnitSchemaServiceModel schema = serviceModelGenerator.generateNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.TYPE, null);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema.propertySchema.type' can't be null",
				errors.iterator().next());
	}
}
