package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		final ResponseEntity<DataUnitSchemaDTO> responseEntity = send(
				HttpMethod.POST,
				"/dataUnitSchema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		final DataUnitSchemaDTO responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		final Long responseSchemaId = responseSchema.getId();
		Assertions.assertNotNull(responseSchemaId);
		schemaAsserter.assertEquals(schema, responseSchema, AbstractEntity_.ID);

		final DataUnitSchemaEntity savedSchema = testDAO.findById(responseSchemaId);
		Assertions.assertNotNull(savedSchema);
		schemaAsserter.assertEquals(savedSchema, responseSchema);

		testDAO.deleteById(savedSchema.getId());
	}

	@Test
	void postSchemaNotNullIdTest() {
		final DataUnitSchemaDTO schema = dtoGenerator.generateObject();
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

		final DataUnitSchemaEntity savedSchema = testDAO.findById(schema.getId());
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME, name);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME,
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
		final DataUnitSchemaEntity savedSchema = testDAO.save(entityGenerator.generateObjectNullId());
		final String savedSchemaName = savedSchema.getName();

		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.NAME, savedSchemaName);
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

	private void processPostSchemaNotValidPropertySchemasTest(final List<DataUnitPropertySchemaDTO> propertySchemas) {
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, propertySchemas);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		final List<DataUnitPropertySchemaDTO> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		propertySchemas.add(null);
		TestReflectionUtils.setField(schema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, propertySchemas);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		final List<DataUnitPropertySchemaDTO> propertySchemas = new ArrayList<>(schema.getPropertySchemas());
		final Long id = dtoGenerator.generateObject().getPropertySchemas().
				iterator().next().getId();
		TestReflectionUtils.setField(propertySchemas.iterator().next(), AbstractEntity_.ID, id);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaEntity_.NAME, name);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(), DataUnitPropertySchemaEntity_.NAME,
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		final List<DataUnitPropertySchemaDTO> propertySchemas = schema.getPropertySchemas();
		final String duplicateName = propertySchemas.get(0).getName();
		TestReflectionUtils.setField(propertySchemas.get(1),
				DataUnitPropertySchemaEntity_.NAME, duplicateName);
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
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaEntity_.TYPE, null);
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
