package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import java.time.LocalDate;

class DataUnitPropertyValueDateTypeChecker extends AbstractDataUnitPropertyValueTypeChecker {

	@Override
	protected Class<?> getTypeCoreClass() {
		return LocalDate.class;
	}

}
