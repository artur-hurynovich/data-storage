package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TEXT_PROPERTY_VALUE;
import static com.hurynovich.data_storage.model.ModelConstants.DATA_UNIT_TIME_PROPERTY_VALUE;

class DataUnitPropertyValueTimeTypeCheckerTest {

	private final DataUnitPropertyValueTypeChecker checker = new DataUnitPropertyValueTimeTypeChecker();

	@Test
	void checkTrue() {
		Assertions.assertTrue(checker.check(DATA_UNIT_TIME_PROPERTY_VALUE));
	}

	@Test
	void checkFalse() {
		Assertions.assertFalse(checker.check(DATA_UNIT_TEXT_PROPERTY_VALUE));
	}
}
