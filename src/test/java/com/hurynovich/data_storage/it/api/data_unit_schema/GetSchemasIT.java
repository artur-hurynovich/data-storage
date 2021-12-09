package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class GetSchemasIT extends AbstractDataUnitSchemaIT {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getSchemasTest() {
		final List<DataUnitSchemaPersistentModel> existingSchemas = persistentModelGenerator.
				generateListNullId().stream().
				map(testDAO::save).
				collect(Collectors.toList());
		final ResponseEntity<String> responseEntity = send(
				HttpMethod.GET,
				"/dataUnitSchemas?pageNumber=1",
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity);

		final String responseBodyJson = responseEntity.getBody();
		Assertions.assertNotNull(responseBodyJson);
		final GenericPage<DataUnitSchemaServiceModel> responsePage = parseGetSchemasResponseBody(responseBodyJson);
		Assertions.assertNotNull(responsePage);
		final List<DataUnitSchemaServiceModel> responseSchemas = responsePage.getElements();
		Assertions.assertNotNull(responseSchemas);
		Assertions.assertEquals(existingSchemas.size(), responseSchemas.size());
		for (int i = 0; i < existingSchemas.size(); i++) {
			final DataUnitSchemaServiceModel responseSchema = responseSchemas.get(i);
			schemaAsserter.assertEquals(existingSchemas.get(i), responseSchema,
					DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);

			final List<DataUnitPropertySchemaServiceModel> propertySchemas = responseSchema.getPropertySchemas();
			Assertions.assertNotNull(propertySchemas);
			Assertions.assertTrue(propertySchemas.isEmpty());
		}

		Assertions.assertEquals(existingSchemas.size(), responsePage.getTotalElementsCount());
		Assertions.assertNull(responsePage.getPreviousPageNumber());
		Assertions.assertEquals(1, responsePage.getCurrentPageNumber());
		Assertions.assertNull(responsePage.getNextPageNumber());
		Assertions.assertEquals(1, responsePage.getTotalPagesCount());

		existingSchemas.forEach(savedSchema -> testDAO.deleteById(savedSchema.getId()));
	}

	@Test
	void getSchemasWithoutPageNumberTest() {
		final List<DataUnitSchemaPersistentModel> existingSchemas = persistentModelGenerator.
				generateListNullId().stream().
				map(testDAO::save).
				collect(Collectors.toList());
		final ResponseEntity<String> responseEntity = send(
				HttpMethod.GET,
				"/dataUnitSchemas",
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity);

		final String responseBodyJson = responseEntity.getBody();
		Assertions.assertNotNull(responseBodyJson);
		final GenericPage<DataUnitSchemaServiceModel> responsePage = parseGetSchemasResponseBody(responseBodyJson);
		Assertions.assertNotNull(responsePage);
		final List<DataUnitSchemaServiceModel> responseSchemas = responsePage.getElements();
		Assertions.assertNotNull(responseSchemas);
		Assertions.assertEquals(existingSchemas.size(), responseSchemas.size());
		for (int i = 0; i < existingSchemas.size(); i++) {
			final DataUnitSchemaServiceModel responseSchema = responseSchemas.get(i);
			schemaAsserter.assertEquals(existingSchemas.get(i), responseSchema,
					DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS);

			final List<DataUnitPropertySchemaServiceModel> propertySchemas = responseSchema.getPropertySchemas();
			Assertions.assertNotNull(propertySchemas);
			Assertions.assertTrue(propertySchemas.isEmpty());
		}

		Assertions.assertEquals(existingSchemas.size(), responsePage.getTotalElementsCount());
		Assertions.assertNull(responsePage.getPreviousPageNumber());
		Assertions.assertEquals(1, responsePage.getCurrentPageNumber());
		Assertions.assertNull(responsePage.getNextPageNumber());
		Assertions.assertEquals(1, responsePage.getTotalPagesCount());

		existingSchemas.forEach(savedSchema -> testDAO.deleteById(savedSchema.getId()));
	}

	/*
	 * TestRestTemplate can't build GenericPage from JSON because GenericPage can only
	 * be built with GenericPageBuilder. So we have to parse JSON ourselves.
	 */
	private GenericPage<DataUnitSchemaServiceModel> parseGetSchemasResponseBody(
			final String responseBodyJson) {
		try {
			final JsonNode rootNode = objectMapper.readTree(responseBodyJson);
			final JsonNode elementsNode = rootNode.get("elements");
			final List<DataUnitSchemaServiceModel> schemas = StreamSupport.stream(elementsNode.spliterator(), false).
					map(this::buildSchema).
					collect(Collectors.toList());

			final Long totalElementsCount = buildNullableLong(rootNode.get("totalElementsCount"));
			final Long previousPageNumber = buildNullableLong(rootNode.get("previousPageNumber"));
			final Long currentPageNumber = buildNullableLong(rootNode.get("currentPageNumber"));
			final Long nextPageNumber = buildNullableLong(rootNode.get("nextPageNumber"));
			final Long totalPagesCount = buildNullableLong(rootNode.get("totalPagesCount"));

			return GenericPage.
					builder(schemas).
					withTotalElementsCount(totalElementsCount).
					withPreviousPageNumber(previousPageNumber).
					withCurrentPageNumber(currentPageNumber).
					withNextPageNumber(nextPageNumber).
					withTotalPagesCount(totalPagesCount).
					build();
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private DataUnitSchemaServiceModel buildSchema(final JsonNode schemaNode) {
		return new DataUnitSchemaServiceModelImpl(schemaNode.get(AbstractServiceModel_.ID).asLong(),
				schemaNode.get(DataUnitSchemaServiceModelImpl_.NAME).asText(),
				buildPropertySchemas(schemaNode.get(DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS)));
	}

	private List<DataUnitPropertySchemaServiceModel> buildPropertySchemas(final JsonNode propertySchemasNode) {
		return StreamSupport.stream(propertySchemasNode.spliterator(), false).
				map(this::buildPropertySchema).
				collect(Collectors.toList());
	}

	private Long buildNullableLong(final JsonNode node) {
		final String asText = node.asText();
		return asText.equals("null") ? null : Long.valueOf(asText);
	}

	private DataUnitPropertySchemaServiceModel buildPropertySchema(final JsonNode propertySchemaNode) {
		return new DataUnitPropertySchemaServiceModelImpl(propertySchemaNode.get(AbstractServiceModel_.ID).asLong(),
				propertySchemaNode.get(DataUnitPropertySchemaServiceModelImpl_.NAME).asText(),
				DataUnitPropertyType.valueOf(propertySchemaNode.get(DataUnitPropertySchemaServiceModelImpl_.TYPE).asText()));
	}
}
