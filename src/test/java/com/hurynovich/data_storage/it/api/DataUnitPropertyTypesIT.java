package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

class DataUnitPropertyTypesIT extends AbstractAPITest {

	@Test
	void getPropertyTypesTest() {
		requestAsserter.assertRequest(HttpMethod.GET, "/dataUnitPropertyTypes", HttpStatus.OK,
				Arrays.asList(DataUnitPropertyType.values()));
	}

}
