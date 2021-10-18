package com.hurynovich.data_storage.validator;

import com.hurynovich.data_storage.model.dto.AbstractDTO;
import com.hurynovich.data_storage.validator.model.ValidationResult;

import java.io.Serializable;

public interface DTOValidator<T extends AbstractDTO<? extends Serializable>> {

	ValidationResult validate(T t);

}
