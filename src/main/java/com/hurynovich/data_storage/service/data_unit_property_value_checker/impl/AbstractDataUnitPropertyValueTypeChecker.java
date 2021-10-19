package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;

public abstract class AbstractDataUnitPropertyValueTypeChecker implements DataUnitPropertyValueTypeChecker {

	@Override
	public boolean check(final Object value) {
		return value.getClass().equals(getTypeCoreClass());
	}

	protected abstract Class<?> getTypeCoreClass();

}
