package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

public class DataUnitPropertyValueIntegerTypeChecker extends AbstractDataUnitPropertyValueTypeChecker {

	@Override
	protected Class<?> getTypeCoreClass() {
		return Integer.class;
	}

}
