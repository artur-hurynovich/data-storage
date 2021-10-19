package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.model.DataUnitPropertyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Set;

class DataUnitPropertyTypesControllerTest {

	private final DataUnitPropertyTypesController controller = new DataUnitPropertyTypesController();

	@Test
	void getPropertyTypesTest() {
		final ResponseEntity<Set<DataUnitPropertyType>> response = controller.getPropertyTypes();
		Assertions.assertNotNull(response);

		final Set<DataUnitPropertyType> propertyTypes = response.getBody();
		Assertions.assertNotNull(propertyTypes);
		Assertions.assertFalse(propertyTypes.isEmpty());

		final DataUnitPropertyType[] expectedPropertyTypes = DataUnitPropertyType.values();
		Assertions.assertEquals(expectedPropertyTypes.length, propertyTypes.size());

		for (final DataUnitPropertyType expectedPropertyType : expectedPropertyTypes) {
			Assertions.assertTrue(propertyTypes.contains(expectedPropertyType));
		}
	}

}
