package com.hurynovich.data_storage.validator;

import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.springframework.lang.NonNull;

public interface Validator<T> {

	ValidationResult validate(@NonNull T t);

}
