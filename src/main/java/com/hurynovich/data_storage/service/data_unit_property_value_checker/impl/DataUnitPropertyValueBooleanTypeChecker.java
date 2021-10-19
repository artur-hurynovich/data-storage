package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

public class DataUnitPropertyValueBooleanTypeChecker extends AbstractDataUnitPropertyValueTypeChecker {

	@Override
	protected Class<?> getTypeCoreClass() {
		return Boolean.class;
	}

}
