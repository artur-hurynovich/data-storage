package com.hurynovich.data_storage.validator;

import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public interface ValidationHelper {

	void applyIsNotNullError(@NonNull String targetName, @NonNull ValidationResult result);

	void applyIsNullError(@NonNull String targetName, @NonNull ValidationResult result);

	void applyIsEmptyError(@NonNull String targetName, @NonNull ValidationResult result);

	void applyIsBlankError(@NonNull String targetName, @NonNull ValidationResult result);

	void applyMaxLengthExceededError(@NonNull String targetName, int maxLength, @NonNull ValidationResult result);

	void applyFoundDuplicateError(@NonNull String targetName, @NonNull Object duplicateValue,
								  @NonNull ValidationResult result);

	void applyNotFoundByIdError(@NonNull String targetName, @NonNull Serializable id,
								@NonNull ValidationResult result);

}
