package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Set;

class DataUnitPropertyTypesIT extends AbstractAPITest {

	@Test
	void getPropertyTypesTest() {
		final ResponseEntity<Set<DataUnitPropertyType>> responseEntity = send(
				HttpMethod.GET,
				"/dataUnitPropertyTypes",
				new ParameterizedTypeReference<>() {
				});

		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final Set<DataUnitPropertyType> body = responseEntity.getBody();
		Assertions.assertNotNull(body);
		Assertions.assertTrue(body.containsAll(Arrays.asList(DataUnitPropertyType.values())));
	}
}
