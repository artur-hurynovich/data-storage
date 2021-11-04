package com.hurynovich.data_storage.validator;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public interface DTOValidator<T extends AbstractDTO<? extends Serializable>> {

	ValidationResult validate(@NonNull T t);

}
