package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

class DataUnitPropertyValueFloatTypeChecker extends AbstractDataUnitPropertyValueTypeChecker {

	@Override
	protected Class<?> getTypeCoreClass() {
		return Double.class;
	}

}
