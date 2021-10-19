package com.hurynovich.data_storage.service.data_unit_property_value_checker;

import org.springframework.lang.NonNull;

public interface DataUnitPropertyValueTypeChecker {

	boolean check(@NonNull Object value);

}
