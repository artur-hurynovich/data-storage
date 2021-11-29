package com.hurynovich.data_storage.validator;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public interface ValidationErrorMessageBuilder {

	String buildIsNotNullErrorMessage(@NonNull String targetName);

	String buildIsNullErrorMessage(@NonNull String targetName);

	String buildIsEmptyErrorMessage(@NonNull String targetName);

	String buildIsBlankErrorMessage(@NonNull String targetName);

	String buildMaxLengthExceededErrorMessage(@NonNull String targetName, int maxLength);

	String buildFoundDuplicateErrorMessage(@NonNull String targetName, @NonNull Object duplicateValue);

	String buildNotFoundByIdErrorMessage(@NonNull String targetName, @NonNull Serializable id);

}
