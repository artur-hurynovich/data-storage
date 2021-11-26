package com.hurynovich.data_storage.it.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.dto_service.BaseService;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.test_objects_asserter.TestObjectsAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitSchemaAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.ValidationResultAsserter;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class DataUnitSchemaIT extends AbstractAPITest {

	@Autowired
	private BaseService<DataUnitSchemaDTO, Long> service;

	private static final String UPDATED_SCHEMA_NAME = "Schema Name UPD";

	private static final String UPDATED_PROPERTY_SCHEMA_NAME = "Property Schema Name UPD";

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestObjectsAsserter<ValidationResult> validationResultAsserter =
			new ValidationResultAsserter();

	private final TestIdentifiedObjectsAsserter<DataUnitSchemaDTO, DataUnitSchemaEntity> schemaAsserter =
			new DataUnitSchemaAsserter();

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final ValidationResult successValidationResult = new ValidationResult();

	@Test
	void postSchemaTest() {
		final DataUnitSchemaDTO schema = dtoGenerator.generateObjectNullId();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> responseEntity = send(
				HttpMethod.POST,
				"/schema",
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = responseEntity.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = responseBody.getValidationResult();
		Assertions.assertNotNull(validationResult);
		validationResultAsserter.assertEquals(successValidationResult, validationResult);

		final DataUnitSchemaDTO responseSchema = responseBody.getBody();
		Assertions.assertNotNull(responseSchema);
		final Long responseSchemaId = responseSchema.getId();
		Assertions.assertNotNull(responseSchemaId);
		schemaAsserter.assertEquals(schema, responseSchema, AbstractEntity_.ID);

		final Optional<DataUnitSchemaDTO> savedSchemaOptional = service.findById(responseSchemaId);
		Assertions.assertTrue(savedSchemaOptional.isPresent());
		final DataUnitSchemaDTO savedSchema = savedSchemaOptional.get();
		Assertions.assertNotNull(savedSchema);
		Assertions.assertEquals(savedSchema.getId(), responseSchemaId);
		schemaAsserter.assertEquals(schema, savedSchema, AbstractEntity_.ID);

		service.deleteById(responseSchemaId);
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaDTO existingSchema = service.save(dtoGenerator.generateObjectNullId());
		final Long existingSchemaId = existingSchema.getId();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> responseEntity = send(
				HttpMethod.GET,
				"/schema/" + existingSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = responseEntity.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = responseBody.getValidationResult();
		Assertions.assertNotNull(validationResult);
		validationResultAsserter.assertEquals(successValidationResult, validationResult);

		final DataUnitSchemaDTO responseSchema = responseBody.getBody();
		Assertions.assertNotNull(responseSchema);
		final Long responseSchemaId = responseSchema.getId();
		Assertions.assertNotNull(responseSchemaId);
		Assertions.assertEquals(existingSchemaId, responseSchemaId);
		schemaAsserter.assertEquals(existingSchema, responseSchema, AbstractEntity_.ID);
	}

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaDTO> existingSchemas = dtoGenerator.generateObjectsNullId().stream().
				map(service::save).
				collect(Collectors.toList());
		final ResponseEntity<String> responseEntity = send(
				HttpMethod.GET,
				"/schemas?pageNumber=1",
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity);

		final String responseBodyJson = responseEntity.getBody();
		Assertions.assertNotNull(responseBodyJson);
		final GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>> responseBody =
				parseGetSchemasResponseBody(responseBodyJson);
		final ValidationResult validationResult = responseBody.getValidationResult();
		Assertions.assertNotNull(validationResult);
		validationResultAsserter.assertEquals(successValidationResult, validationResult);

		final GenericPage<DataUnitSchemaDTO> page = responseBody.getBody();
		Assertions.assertNotNull(page);

		final List<DataUnitSchemaDTO> responseSchemas = page.getElements();
		Assertions.assertNotNull(responseSchemas);
		Assertions.assertEquals(existingSchemas.size(), responseSchemas.size());
		for (int i = 0; i < existingSchemas.size(); i++) {
			final DataUnitSchemaDTO responseSchema = responseSchemas.get(i);
			schemaAsserter.assertEquals(existingSchemas.get(i), responseSchema,
					DataUnitSchemaEntity_.PROPERTY_SCHEMAS);

			final List<DataUnitPropertySchemaDTO> propertySchemas = responseSchema.getPropertySchemas();
			Assertions.assertNotNull(propertySchemas);
			Assertions.assertTrue(propertySchemas.isEmpty());
		}

		Assertions.assertEquals(existingSchemas.size(), page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertEquals(1, page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(1, page.getTotalPagesCount());
	}

	/*
	 * Unfortunately TestRestTemplate can't build GenericPage from JSON because it should be built
	 * with GenericPageBuilder. So we have to parse JSON ourselves.
	 */
	private GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>> parseGetSchemasResponseBody(
			final String responseBodyJson) {
		try {
			final JsonNode rootNode = objectMapper.readTree(responseBodyJson);

			final JsonNode validationResultNode = rootNode.get("validationResult");
			final ValidationResult validationResult = objectMapper.treeToValue(validationResultNode, ValidationResult.class);

			final JsonNode bodyNode = rootNode.get("body");
			final JsonNode elementsNode = bodyNode.get("elements");
			final List<DataUnitSchemaDTO> schemas = StreamSupport.stream(elementsNode.spliterator(), false).
					map(this::buildSchema).
					collect(Collectors.toList());

			final Long totalElementsCount = buildNullableLong(bodyNode.get("totalElementsCount"));
			final Long previousPageNumber = buildNullableLong(bodyNode.get("previousPageNumber"));
			final Long currentPageNumber = buildNullableLong(bodyNode.get("currentPageNumber"));
			final Long nextPageNumber = buildNullableLong(bodyNode.get("nextPageNumber"));
			final Long totalPagesCount = buildNullableLong(bodyNode.get("totalPagesCount"));

			return new GenericValidatedResponse<>(validationResult, GenericPage.
					builder(schemas).
					withTotalElementsCount(totalElementsCount).
					withPreviousPageNumber(previousPageNumber).
					withCurrentPageNumber(currentPageNumber).
					withNextPageNumber(nextPageNumber).
					withTotalPagesCount(totalPagesCount).
					build());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private DataUnitSchemaDTO buildSchema(final JsonNode schemaNode) {
		return new DataUnitSchemaDTO(schemaNode.get(AbstractEntity_.ID).asLong(),
				schemaNode.get(DataUnitSchemaEntity_.NAME).asText(),
				buildPropertySchemas(schemaNode.get(DataUnitSchemaEntity_.PROPERTY_SCHEMAS)));
	}

	private List<DataUnitPropertySchemaDTO> buildPropertySchemas(final JsonNode propertySchemasNode) {
		return StreamSupport.stream(propertySchemasNode.spliterator(), false).
				map(this::buildPropertySchema).
				collect(Collectors.toList());
	}

	private Long buildNullableLong(final JsonNode node) {
		final String asText = node.asText();
		return asText.equals("null") ? null : Long.valueOf(asText);
	}

	private DataUnitPropertySchemaDTO buildPropertySchema(final JsonNode propertySchemaNode) {
		return new DataUnitPropertySchemaDTO(propertySchemaNode.get(AbstractEntity_.ID).asLong(),
				propertySchemaNode.get(DataUnitPropertySchemaEntity_.NAME).asText(),
				DataUnitPropertyType.valueOf(propertySchemaNode.get(DataUnitPropertySchemaEntity_.TYPE).asText()));
	}

	@Test
	void putSchemaTest() {
		final DataUnitSchemaDTO existingSchema = service.save(dtoGenerator.generateObjectNullId());
		final Long existingSchemaId = existingSchema.getId();
		final DataUnitPropertySchemaDTO existingPropertySchema = existingSchema.getPropertySchemas().iterator().next();
		final DataUnitSchemaDTO schema = new DataUnitSchemaDTO(existingSchemaId,
				UPDATED_SCHEMA_NAME,
				Collections.singletonList(new DataUnitPropertySchemaDTO(
						existingPropertySchema.getId(), UPDATED_PROPERTY_SCHEMA_NAME, DataUnitPropertyType.BOOLEAN)));
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> responseEntity = send(
				HttpMethod.PUT,
				"/schema/" + existingSchema.getId(),
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = responseEntity.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = responseBody.getValidationResult();
		Assertions.assertNotNull(validationResult);
		validationResultAsserter.assertEquals(successValidationResult, validationResult);

		final DataUnitSchemaDTO responseSchema = responseBody.getBody();
		Assertions.assertNotNull(responseSchema);
		final Long responseSchemaId = responseSchema.getId();
		Assertions.assertNotNull(responseSchemaId);
		Assertions.assertEquals(existingSchemaId, responseSchemaId);
		schemaAsserter.assertEquals(schema, responseSchema, AbstractEntity_.ID);

		final Optional<DataUnitSchemaDTO> updatedSchemaOptional = service.findById(existingSchemaId);
		Assertions.assertTrue(updatedSchemaOptional.isPresent());
		final DataUnitSchemaDTO updatedSchema = updatedSchemaOptional.get();
		Assertions.assertNotNull(updatedSchema);
		Assertions.assertEquals(existingSchemaId, updatedSchema.getId());
		schemaAsserter.assertEquals(schema, updatedSchema, AbstractEntity_.ID);

		service.deleteById(existingSchemaId);
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaDTO existingSchema = service.save(dtoGenerator.generateObjectNullId());
		final Long existingSchemaId = existingSchema.getId();
		final ResponseEntity<GenericValidatedResponse<DataUnitSchemaDTO>> responseEntity = send(
				HttpMethod.DELETE,
				"/schema/" + existingSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final GenericValidatedResponse<DataUnitSchemaDTO> responseBody = responseEntity.getBody();
		Assertions.assertNotNull(responseBody);

		final ValidationResult validationResult = responseBody.getValidationResult();
		Assertions.assertNotNull(validationResult);
		validationResultAsserter.assertEquals(successValidationResult, validationResult);

		Assertions.assertNull(responseBody.getBody());
		final Optional<DataUnitSchemaDTO> deletedSchemaOptional = service.findById(existingSchemaId);
		Assertions.assertTrue(deletedSchemaOptional.isEmpty());
	}

}
