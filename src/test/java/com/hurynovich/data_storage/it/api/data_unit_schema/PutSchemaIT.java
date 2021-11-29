package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

class PutSchemaIT extends AbstractDataUnitSchemaIT {

	private static final String UPDATED_SCHEMA_NAME = "Schema Name UPD";

	private static final String UPDATED_PROPERTY_SCHEMA_NAME = "Property Schema Name UPD";

	@Test
	void putSchemaTest() {
		final DataUnitSchemaEntity savedSchema = testDAO.save(entityGenerator.generateObjectNullId());
		final Long savedSchemaId = savedSchema.getId();
		final DataUnitPropertySchemaEntity savedPropertySchema = savedSchema.getPropertySchemas().iterator().next();
		final DataUnitSchemaDTO schema = new DataUnitSchemaDTO(savedSchemaId,
				UPDATED_SCHEMA_NAME,
				Collections.singletonList(new DataUnitPropertySchemaDTO(
						savedPropertySchema.getId(), UPDATED_PROPERTY_SCHEMA_NAME, DataUnitPropertyType.BOOLEAN)));
		final ResponseEntity<DataUnitSchemaDTO> responseEntity = send(
				HttpMethod.PUT,
				"/dataUnitSchema/" + savedSchema.getId(),
				schema,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final DataUnitSchemaDTO responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		schemaAsserter.assertEquals(schema, responseSchema);

		final DataUnitSchemaEntity updatedSchema = testDAO.findById(savedSchemaId);
		schemaAsserter.assertEquals(schema, updatedSchema);

		testDAO.deleteById(savedSchemaId);
	}
}
