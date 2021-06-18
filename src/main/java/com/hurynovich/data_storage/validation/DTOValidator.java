package com.hurynovich.data_storage.validation;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.ValidationResult;

import java.io.Serializable;

public interface DTOValidator<T extends Identified<I>, I extends Serializable> {

	ValidationResult validateOnSave(T dto);

	ValidationResult validateOnUpdate(T dto);

	ValidationResult validateOnDelete(I id);

}
