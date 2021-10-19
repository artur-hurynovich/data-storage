package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_DATE_PROPERTY_VALUE;
import static com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

class DataUnitPropertyValueDateTypeCheckerTest {

	private final DataUnitPropertyValueTypeChecker checker = new DataUnitPropertyValueDateTypeChecker();

	@Test
	void checkTrue() {
		final boolean result = checker.check(DATA_UNIT_DATE_PROPERTY_VALUE);
		Assertions.assertTrue(result);
	}

	@Test
	void checkFalse() {
		final boolean result = checker.check(DATA_UNIT_TIME_PROPERTY_VALUE);
		Assertions.assertFalse(result);
	}

}
