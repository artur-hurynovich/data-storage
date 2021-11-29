package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetSchemaByIdIT extends AbstractDataUnitSchemaIT {

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaEntity savedSchema = testDAO.save(entityGenerator.generateObjectNullId());
		final Long savedSchemaId = savedSchema.getId();
		final ResponseEntity<DataUnitSchemaDTO> responseEntity = send(
				HttpMethod.GET,
				"/dataUnitSchema/" + savedSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final DataUnitSchemaDTO responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		Assertions.assertNotNull(responseSchema);
		schemaAsserter.assertEquals(savedSchema, responseSchema);

		testDAO.deleteById(savedSchemaId);
	}
}
