package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

class DeleteSchemaByIdIT extends AbstractDataUnitSchemaIT {

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaPersistentModel savedSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final Long savedSchemaId = savedSchema.getId();
		final ResponseEntity<DataUnitSchemaApiModel> responseEntity = send(
				HttpMethod.DELETE,
				"/dataUnitSchema/" + savedSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		Assertions.assertNull(responseEntity.getBody());

		final DataUnitSchemaPersistentModel deletedSchema = testDAO.findById(savedSchemaId);
		Assertions.assertNull(deletedSchema);
	}

	@Test
	void deleteSchemaByIdNotFoundTest() {
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.DELETE,
				"/dataUnitSchema/" + INCORRECT_LONG_ID,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		final Set<String> errors = responseEntity.getBody();
		Assertions.assertNotNull(errors);
		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals("'dataUnitSchema' with id = '" + INCORRECT_LONG_ID + "' not found",
				errors.iterator().next());
	}
}
