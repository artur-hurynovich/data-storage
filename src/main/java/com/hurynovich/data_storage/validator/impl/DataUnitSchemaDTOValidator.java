package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataUnitSchemaDTOValidator implements DTOValidator<DataUnitSchemaDTO> {

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	private final DataUnitSchemaDTOService service;

	public DataUnitSchemaDTOValidator(final @NonNull DataUnitSchemaDTOService service) {
		this.service = service;
	}

	@Override
	public ValidationResult validate(final @Nullable DataUnitSchemaDTO schema) {
		final ValidationResult result = new ValidationResult();

		if (schema == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnitSchema' can't be null");
		} else {
			final String name = schema.getName();
			if (StringUtils.isBlank(name)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnitSchema.name' can't be null, empty or blank");
			} else {
				if (name.length() > DATA_UNIT_SCHEMA_NAME_MAX_LENGTH) {
					result.setType(ValidationResultType.FAILURE);
					result.addError("'dataUnitSchema.name' can't exceed " +
							DATA_UNIT_SCHEMA_NAME_MAX_LENGTH + " characters");
				}

				checkNameForDuplicates(schema.getId(), name, result);
			}

			final List<DataUnitPropertySchemaDTO> propertySchemas = schema.getPropertySchemas();
			if (CollectionUtils.isEmpty(propertySchemas)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnitSchema.propertySchemas' can't be null or empty");
			} else {
				validatePropertySchemas(propertySchemas, result);
			}
		}

		return result;
	}

	private void checkNameForDuplicates(final @Nullable Long id, final @NonNull String name,
										final @NonNull ValidationResult result) {
		if ((id == null && service.existsByName(name))
				|| (id != null && service.existsByNameAndNotId(name, id))) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("Found duplicate '" + name + "' for 'dataUnitSchema.name'");
		}
	}

	private void validatePropertySchemas(final @NonNull List<DataUnitPropertySchemaDTO> propertySchemas,
										 final @NonNull ValidationResult result) {
		final Set<String> uniquePropertySchemaNames = new HashSet<>();

		propertySchemas.forEach(propertySchema ->
				validatePropertySchema(propertySchema, uniquePropertySchemaNames, result));
	}

	private void validatePropertySchema(final @Nullable DataUnitPropertySchemaDTO propertySchema,
										final @NonNull Set<String> uniquePropertySchemaNames,
										final @NonNull ValidationResult result) {
		if (propertySchema == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnitSchema.propertySchema' can't be null");
		} else {
			final String name = propertySchema.getName();
			if (StringUtils.isBlank(name)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnitSchema.propertySchema.name' can't be null, empty or blank");
			} else {
				if (name.length() > DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH) {
					result.setType(ValidationResultType.FAILURE);
					result.addError("'dataUnitSchema.propertySchema.name' can't exceed " +
							DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH + " characters");
				}

				if (!uniquePropertySchemaNames.add(name)) {
					result.setType(ValidationResultType.FAILURE);
					result.addError("Found duplicate '" + name + "' for 'dataUnitSchema.propertySchema.name'");
				}
			}

			if (propertySchema.getType() == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnitSchema.propertySchema.type' can't be null");
			}
		}
	}

}
