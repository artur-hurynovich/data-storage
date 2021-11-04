package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.validator.DTOValidationHelper;
import com.hurynovich.data_storage.validator.DTOValidator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataUnitSchemaDTOValidator implements DTOValidator<DataUnitSchemaDTO> {

	private static final String DATA_UNIT_SCHEMA_NAME = "dataUnitSchema.name";

	private static final String DATA_UNIT_PROPERTY_SCHEMA_NAME = "dataUnitSchema.propertySchema.name";

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	private final DTOValidationHelper helper;

	private final DataUnitSchemaDTOService service;

	public DataUnitSchemaDTOValidator(final @NonNull DTOValidationHelper helper,
									  final @NonNull DataUnitSchemaDTOService service) {
		this.helper = helper;
		this.service = service;
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitSchemaDTO schema) {
		final ValidationResult result = new ValidationResult();
		final Long id = schema.getId();
		final String name = schema.getName();
		if (StringUtils.isBlank(name)) {
			helper.applyIsBlankError(DATA_UNIT_SCHEMA_NAME, result);
		} else {
			if (name.length() > DATA_UNIT_SCHEMA_NAME_MAX_LENGTH) {
				helper.applyMaxLengthExceededError(DATA_UNIT_SCHEMA_NAME,
						DATA_UNIT_SCHEMA_NAME_MAX_LENGTH, result);
			}

			if ((id == null && service.existsByName(name))
					|| (id != null && service.existsByNameAndNotId(name, id))) {
				helper.applyFoundDuplicateError(DATA_UNIT_SCHEMA_NAME, name, result);
			}
		}

		final List<DataUnitPropertySchemaDTO> propertySchemas = schema.getPropertySchemas();
		if (CollectionUtils.isEmpty(propertySchemas)) {
			helper.applyIsEmptyError("dataUnitSchema.propertySchemas", result);
		} else {
			validatePropertySchemas(id, propertySchemas, result);
		}

		return result;
	}

	private void validatePropertySchemas(final @Nullable Long schemaId,
										 final @NonNull List<DataUnitPropertySchemaDTO> propertySchemas,
										 final @NonNull ValidationResult result) {
		final Set<Long> validPropertySchemaIds = new HashSet<>();
		if (schemaId != null) {
			service.findById(schemaId).ifPresent(dataUnitSchemaDTO -> validPropertySchemaIds.addAll(
					dataUnitSchemaDTO.getPropertySchemas().stream().
							map(AbstractDTO::getId).
							collect(Collectors.toSet())));
		}

		final Set<String> uniquePropertySchemaNames = new HashSet<>();

		propertySchemas.forEach(propertySchema ->
				validatePropertySchema(schemaId, validPropertySchemaIds, propertySchema, uniquePropertySchemaNames, result));
	}

	private void validatePropertySchema(final @Nullable Long schemaId,
										final @NonNull Set<Long> validPropertySchemaIds,
										final @Nullable DataUnitPropertySchemaDTO propertySchema,
										final @NonNull Set<String> uniquePropertySchemaNames,
										final @NonNull ValidationResult result) {
		if (propertySchema == null) {
			helper.applyIsNullError("dataUnitSchema.propertySchema", result);
		} else {
			final Long propertySchemaId = propertySchema.getId();
			if ((propertySchemaId != null)
					&& (schemaId == null || (!validPropertySchemaIds.contains(propertySchemaId)))) {
				helper.applyNotFoundByIdError("dataUnitPropertySchema", propertySchemaId, result);
			}

			validatePropertySchemaName(propertySchema.getName(), uniquePropertySchemaNames, result);

			if (propertySchema.getType() == null) {
				helper.applyIsNullError("dataUnitSchema.propertySchema.type", result);
			}
		}
	}

	private void validatePropertySchemaName(final @Nullable String name, final @NonNull Set<String> uniquePropertySchemaNames,
											final @NonNull ValidationResult result) {
		if (StringUtils.isBlank(name)) {
			helper.applyIsBlankError(DATA_UNIT_PROPERTY_SCHEMA_NAME, result);
		} else {
			if (name.length() > DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH) {
				helper.applyMaxLengthExceededError(DATA_UNIT_PROPERTY_SCHEMA_NAME,
						DATA_UNIT_SCHEMA_NAME_MAX_LENGTH, result);
			}

			if (!uniquePropertySchemaNames.add(name)) {
				helper.applyFoundDuplicateError(DATA_UNIT_PROPERTY_SCHEMA_NAME,
						name, result);
			}
		}
	}

}
