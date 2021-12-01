package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
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
		final List<DataUnitSchemaEntity> existingSchemas = entityGenerator.generateObjectsNullId().stream().
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
		final GenericPage<DataUnitSchemaDTO> responsePage = parseGetSchemasResponseBody(responseBodyJson);
		Assertions.assertNotNull(responsePage);
		final List<DataUnitSchemaDTO> responseSchemas = responsePage.getElements();
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

		Assertions.assertEquals(existingSchemas.size(), responsePage.getTotalElementsCount());
		Assertions.assertNull(responsePage.getPreviousPageNumber());
		Assertions.assertEquals(1, responsePage.getCurrentPageNumber());
		Assertions.assertNull(responsePage.getNextPageNumber());
		Assertions.assertEquals(1, responsePage.getTotalPagesCount());

		existingSchemas.forEach(savedSchema -> testDAO.deleteById(savedSchema.getId()));
	}

	@Test
	void getSchemasWithoutPageNumberTest() {
		final List<DataUnitSchemaEntity> existingSchemas = entityGenerator.generateObjectsNullId().stream().
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
		final GenericPage<DataUnitSchemaDTO> responsePage = parseGetSchemasResponseBody(responseBodyJson);
		Assertions.assertNotNull(responsePage);
		final List<DataUnitSchemaDTO> responseSchemas = responsePage.getElements();
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

		Assertions.assertEquals(existingSchemas.size(), responsePage.getTotalElementsCount());
		Assertions.assertNull(responsePage.getPreviousPageNumber());
		Assertions.assertEquals(1, responsePage.getCurrentPageNumber());
		Assertions.assertNull(responsePage.getNextPageNumber());
		Assertions.assertEquals(1, responsePage.getTotalPagesCount());

		existingSchemas.forEach(savedSchema -> testDAO.deleteById(savedSchema.getId()));
	}

	/*
	 * Unfortunately TestRestTemplate can't build GenericPage from JSON because GenericPage can only
	 * be built with GenericPageBuilder. So we have to parse JSON ourselves.
	 */
	private GenericPage<DataUnitSchemaDTO> parseGetSchemasResponseBody(
			final String responseBodyJson) {
		try {
			final JsonNode rootNode = objectMapper.readTree(responseBodyJson);
			final JsonNode elementsNode = rootNode.get("elements");
			final List<DataUnitSchemaDTO> schemas = StreamSupport.stream(elementsNode.spliterator(), false).
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
}
