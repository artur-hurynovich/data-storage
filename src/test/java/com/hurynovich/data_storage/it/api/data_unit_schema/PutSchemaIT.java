package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModelImpl;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelImpl;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
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
import java.util.stream.Collectors;

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE;
import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

class PutSchemaIT extends AbstractDataUnitSchemaIT {

	private static final String UPDATED_SCHEMA_NAME = "Schema Name UPD";

	private static final String UPDATED_PROPERTY_SCHEMA_NAME = "Property Schema Name UPD";

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	@Test
	void putSchemaTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final Long existingSchemaId = existingSchema.getId();
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final ResponseEntity<DataUnitSchemaApiModel> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchema.getId(),
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final DataUnitSchemaApiModel responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		schemaAsserter.assertEquals(schema, responseSchema);

		final DataUnitSchemaPersistentModel updatedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(schema, updatedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNullIdTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema, AbstractServiceModel_.ID, null);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaIncorrectIdTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema, AbstractServiceModel_.ID, INCORRECT_LONG_ID);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertTrue(errors.contains("'dataUnitSchema.id' should be equal to path variable 'id'"));

		final DataUnitSchemaPersistentModel savedSchemaWithIncorrectId = testDAO.findById(INCORRECT_LONG_ID);
		Assertions.assertNull(savedSchemaWithIncorrectId);

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaSameNameTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final Long existingSchemaId = existingSchema.getId();
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, existingSchema.getName());
		final ResponseEntity<DataUnitSchemaApiModel> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchema.getId(),
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final DataUnitSchemaApiModel responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		schemaAsserter.assertEquals(schema, responseSchema);

		final DataUnitSchemaPersistentModel updatedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(schema, updatedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNullNameTest() {
		processPutSchemaNotValidNameTest(null);
	}

	@Test
	void putSchemaEmptyNameTest() {
		processPutSchemaNotValidNameTest(StringUtils.EMPTY);
	}

	@Test
	void putSchemaBlankNameTest() {
		processPutSchemaNotValidNameTest(StringUtils.SPACE);
	}

	private void processPutSchemaNotValidNameTest(final String name) {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, name);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNameMaxLengthTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + 1));
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNameDuplicateTest() {
		final List<DataUnitSchemaPersistentModel> existingSchemas = persistentModelGenerator.generateListNullId().stream().
				map(testDAO::save).
				collect(Collectors.toList());
		final DataUnitSchemaPersistentModel existingSchema = existingSchemas.get(0);
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		final String duplicateName = existingSchemas.get(1).getName();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.NAME, duplicateName);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("Found duplicate '" + duplicateName + "' for 'dataUnitSchema.name'",
				errors.iterator().next());

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void processPutSchemaNullPropertySchemasTest() {
		processPutSchemaNotValidPropertySchemasTest(null);
	}

	@Test
	void processPutSchemaEmptyPropertySchemasTest() {
		processPutSchemaNotValidPropertySchemasTest(new ArrayList<>());
	}

	private void processPutSchemaNotValidPropertySchemasTest(
			final List<DataUnitPropertySchemaPersistentModel> propertySchemas) {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNullPropertySchemaTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		final List<DataUnitPropertySchemaApiModel> propertySchemas =
				new ArrayList<>(schema.getPropertySchemas());
		propertySchemas.add(null);
		TestReflectionUtils.setField(schema, DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS, propertySchemas);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNullPropertySchemaNameTest() {
		processPutSchemaNotValidPropertySchemaNameTest(null);
	}

	@Test
	void putSchemaEmptyPropertySchemaNameTest() {
		processPutSchemaNotValidPropertySchemaNameTest(StringUtils.EMPTY);
	}

	@Test
	void putSchemaBlankPropertySchemaNameTest() {
		processPutSchemaNotValidPropertySchemaNameTest(StringUtils.SPACE);
	}

	private void processPutSchemaNotValidPropertySchemaNameTest(final String name) {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.NAME, name);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaPropertySchemaNameMaxLengthTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.NAME,
				RandomStringUtils.randomAlphabetic(DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + 1));
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaPropertySchemaNameDuplicateTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		final List<DataUnitPropertySchemaApiModel> propertySchemas = schema.getPropertySchemas();
		final String duplicateName = propertySchemas.get(0).getName();
		TestReflectionUtils.setField(propertySchemas.get(1), DataUnitPropertySchemaServiceModelImpl_.NAME,
				duplicateName);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	@Test
	void putSchemaNullPropertySchemaTypeTest() {
		final DataUnitSchemaPersistentModel existingSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final DataUnitSchemaApiModel schema = buildUpdatedSchema(existingSchema);
		final Long existingSchemaId = schema.getId();
		TestReflectionUtils.setField(schema.getPropertySchemas().iterator().next(),
				DataUnitPropertySchemaServiceModelImpl_.TYPE, null);
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + existingSchemaId,
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

		final DataUnitSchemaPersistentModel savedSchema = testDAO.findById(existingSchemaId);
		schemaAsserter.assertEquals(existingSchema, savedSchema);

		testDAO.deleteById(existingSchemaId);
	}

	private DataUnitSchemaApiModel buildUpdatedSchema(final DataUnitSchemaPersistentModel existingSchema) {
		final Long savedSchemaId = existingSchema.getId();
		final List<DataUnitPropertySchemaPersistentModel> existingPropertySchemas = existingSchema.
				getPropertySchemas();
		final DataUnitPropertySchemaPersistentModel existingPropertySchema1 = existingPropertySchemas.get(0);
		final DataUnitPropertySchemaPersistentModel existingPropertySchema2 = existingPropertySchemas.get(1);

		return new DataUnitSchemaApiModelImpl(savedSchemaId,
				UPDATED_SCHEMA_NAME,
				List.of(new DataUnitPropertySchemaApiModelImpl(existingPropertySchema1.getId(),
								UPDATED_PROPERTY_SCHEMA_NAME, DATA_UNIT_BOOLEAN_PROPERTY_SCHEMA_TYPE),
						new DataUnitPropertySchemaApiModelImpl(existingPropertySchema2.getId(),
								existingPropertySchema2.getName(), existingPropertySchema2.getType())));
	}
}
