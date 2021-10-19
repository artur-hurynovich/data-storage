package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DataUnitSchemaDTOValidator implements DTOValidator<DataUnitSchemaDTO> {

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	@Override
	public ValidationResult validate(final @Nullable DataUnitSchemaDTO dataUnitSchema) {
		final ValidationResult result = new ValidationResult();

		if (dataUnitSchema == null) {
			result.setType(ValidationResultType.FAILURE);

			result.addError("'dataUnitSchema' can't be null");
		} else {
			final String name = dataUnitSchema.getName();
			if (StringUtils.isBlank(name)) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.name' can't be null, empty or blank");
			} else if (name.length() > DATA_UNIT_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.name' can't exceed " +
						DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + " characters");
			}

			final List<DataUnitPropertySchemaDTO> propertySchemas = dataUnitSchema.getPropertySchemas();
			if (CollectionUtils.isEmpty(propertySchemas)) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.propertySchemas' can't be null or empty");
			} else {
				validatePropertySchemas(propertySchemas, result);
			}
		}

		return result;
	}

	private void validatePropertySchemas(final @NonNull List<DataUnitPropertySchemaDTO> propertySchemas,
										 final @NonNull ValidationResult result) {
		propertySchemas.forEach(propertySchema -> validatePropertySchema(propertySchema, result));
	}

	private void validatePropertySchema(final @Nullable DataUnitPropertySchemaDTO propertySchema,
										final @NonNull ValidationResult result) {
		if (propertySchema == null) {
			result.setType(ValidationResultType.FAILURE);

			result.addError("'dataUnitSchema.propertySchema' can't be null");
		} else {
			final String name = propertySchema.getName();
			if (StringUtils.isBlank(name)) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.propertySchema.name' can't be null, empty or blank");
			} else if (name.length() > DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.propertySchema.name' can't exceed " +
						DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + " characters");
			}

			if (propertySchema.getType() == null) {
				result.setType(ValidationResultType.FAILURE);

				result.addError("'dataUnitSchema.propertySchema.type' can't be null");
			}
		}
	}

}
