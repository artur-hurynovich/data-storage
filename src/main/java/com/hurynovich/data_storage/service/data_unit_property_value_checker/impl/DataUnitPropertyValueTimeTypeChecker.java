package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import java.time.LocalTime;

public class DataUnitPropertyValueTimeTypeChecker extends AbstractDataUnitPropertyValueTypeChecker {

	@Override
	protected Class<?> getTypeCoreClass() {
		return LocalTime.class;
	}

}
