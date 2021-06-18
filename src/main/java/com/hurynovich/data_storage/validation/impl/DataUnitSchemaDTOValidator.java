package com.hurynovich.data_storage.validation.impl;

import com.hurynovich.data_storage.model.ValidationResult;
import com.hurynovich.data_storage.model.ValidationResultType;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.utils.ErrorUtils;
import com.hurynovich.data_storage.validation.DTOValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaDTOValidator implements DTOValidator<DataUnitSchemaDTO, Long> {

	@Override
	public ValidationResult validateOnSave(final DataUnitSchemaDTO dataUnitSchema) {
		boolean valid = true;

		final ValidationResult validationResult = new ValidationResult();

		if (dataUnitSchema.getId() != null) {
			valid = false;

			validationResult.addError(ErrorUtils.buildParameterCanNotBePassedError("id"));
		}

		if (StringUtils.isBlank(dataUnitSchema.getName())) {
			valid = false;

			validationResult.addError(ErrorUtils.buildEmptyParameterError("name"));
		}

		if (!valid) {
			validationResult.setType(ValidationResultType.FAILURE);
		}

		return validationResult;
	}

	@Override
	public ValidationResult validateOnUpdate(final DataUnitSchemaDTO dataUnitSchema) {
		boolean valid = true;

		final ValidationResult validationResult = new ValidationResult();

		if (dataUnitSchema.getId() == null) {
			valid = false;

			validationResult.addError(ErrorUtils.buildEmptyParameterError("id"));
		}

		if (StringUtils.isBlank(dataUnitSchema.getName())) {
			valid = false;

			validationResult.addError(ErrorUtils.buildEmptyParameterError("name"));
		}

		if (!valid) {
			validationResult.setType(ValidationResultType.FAILURE);
		}

		return validationResult;
	}

	@Override
	public ValidationResult validateOnDelete(final Long id) {
		boolean valid = true;

		final ValidationResult validationResult = new ValidationResult();

		if (id == null) {
			valid = false;

			validationResult.addError(ErrorUtils.buildEmptyParameterError("id"));
		}

		if (!valid) {
			validationResult.setType(ValidationResultType.FAILURE);
		}

		return validationResult;
	}

}
