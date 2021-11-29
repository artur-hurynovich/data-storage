package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeleteSchemaByIdIT extends AbstractDataUnitSchemaIT {

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaEntity savedSchema = testDAO.save(entityGenerator.generateObjectNullId());
		final Long savedSchemaId = savedSchema.getId();
		final ResponseEntity<DataUnitSchemaDTO> responseEntity = send(
				HttpMethod.DELETE,
				"/dataUnitSchema/" + savedSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		Assertions.assertNull(responseEntity.getBody());

		final DataUnitSchemaEntity deletedSchema = testDAO.findById(savedSchemaId);
		Assertions.assertNull(deletedSchema);
	}
}
