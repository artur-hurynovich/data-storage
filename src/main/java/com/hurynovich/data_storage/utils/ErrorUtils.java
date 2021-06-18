package com.hurynovich.data_storage.utils;

import com.hurynovich.data_storage.model.Error;

import java.io.Serializable;

public class ErrorUtils {

	private static final String EMPTY_PARAMETER_ERROR_CODE = "0000";
	private static final String EMPTY_PARAMETER_ERROR_MESSAGE = "Parameter '%s' can not be empty";

	private static final String PARAMETER_CAN_NOT_BE_PASSED_ERROR_CODE = "0001";
	private static final String PARAMETER_CAN_NOT_BE_PASSED_ERROR_MESSAGE = "Parameter '%s' can not be passed";

	private static final String NOT_FOUND_BY_ID_ERROR_CODE = "0002";
	private static final String NOT_FOUND_BY_ID_ERROR_MESSAGE = "Object with id '%s' not found";

	private ErrorUtils() {

	}

	public static Error buildEmptyParameterError(final String parameterName) {
		return new Error(EMPTY_PARAMETER_ERROR_CODE,
				String.format(EMPTY_PARAMETER_ERROR_MESSAGE, parameterName));
	}

	public static Error buildParameterCanNotBePassedError(final String parameterName) {
		return new Error(PARAMETER_CAN_NOT_BE_PASSED_ERROR_CODE,
				String.format(PARAMETER_CAN_NOT_BE_PASSED_ERROR_MESSAGE, parameterName));
	}

	public static <T extends Serializable> Error buildNotFoundByIdError(final T id) {
		return new Error(NOT_FOUND_BY_ID_ERROR_CODE,
				String.format(NOT_FOUND_BY_ID_ERROR_MESSAGE, id));
	}

}
