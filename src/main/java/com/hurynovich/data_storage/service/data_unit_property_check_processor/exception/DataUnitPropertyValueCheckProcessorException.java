package com.hurynovich.data_storage.service.data_unit_property_check_processor.exception;

import org.springframework.lang.NonNull;

public class DataUnitPropertyValueCheckProcessorException extends RuntimeException {

	public DataUnitPropertyValueCheckProcessorException(final @NonNull String message) {
		super(message);
	}

}
