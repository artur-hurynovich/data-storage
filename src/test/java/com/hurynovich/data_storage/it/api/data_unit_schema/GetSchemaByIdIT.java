package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static com.hurynovich.data_storage.model.ModelConstants.INCORRECT_LONG_ID;

class GetSchemaByIdIT extends AbstractDataUnitSchemaIT {

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaPersistentModel savedSchema = testDAO.save(persistentModelGenerator.generateNullId());
		final Long savedSchemaId = savedSchema.getId();
		final ResponseEntity<DataUnitSchemaServiceModel> responseEntity = send(
				HttpMethod.GET,
				"/dataUnitSchema/" + savedSchemaId,
				new ParameterizedTypeReference<>() {
				});
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final DataUnitSchemaServiceModel responseSchema = responseEntity.getBody();
		Assertions.assertNotNull(responseSchema);
		Assertions.assertNotNull(responseSchema);
		schemaAsserter.assertEquals(savedSchema, responseSchema);

		testDAO.deleteById(savedSchemaId);
	}

	@Test
	void getSchemaByIdNotFoundTest() {
		final ResponseEntity<Set<String>> responseEntity = send(
				HttpMethod.GET,
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
