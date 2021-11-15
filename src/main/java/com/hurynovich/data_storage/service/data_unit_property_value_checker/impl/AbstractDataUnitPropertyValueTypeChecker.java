package com.hurynovich.data_storage.service.data_unit_property_value_checker.impl;

import com.hurynovich.data_storage.service.data_unit_property_value_checker.DataUnitPropertyValueTypeChecker;
import org.springframework.lang.NonNull;

abstract class AbstractDataUnitPropertyValueTypeChecker implements DataUnitPropertyValueTypeChecker {

	@Override
	public boolean check(final @NonNull Object value) {
		return value.getClass().equals(getTypeCoreClass());
	}

	protected abstract Class<?> getTypeCoreClass();

}
