package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class DataUnitSchemaValidator implements Validator<DataUnitSchemaApiModel> {

	private static final String DATA_UNIT_SCHEMA_NAME = "dataUnitSchema.name";

	private static final String DATA_UNIT_PROPERTY_SCHEMA_NAME = "dataUnitSchema.propertySchema.name";

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	private final DataUnitSchemaService schemaService;

	public DataUnitSchemaValidator(final @NonNull DataUnitSchemaService schemaService) {
		this.schemaService = Objects.requireNonNull(schemaService);
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitSchemaApiModel schema) {
		final ValidationResult result = new ValidationResult();
		final Long id = schema.getId();
		final String name = schema.getName();
		if (StringUtils.isBlank(name)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsBlankErrorMessage(DATA_UNIT_SCHEMA_NAME));
		} else {
			if (name.length() > DATA_UNIT_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.
						buildMaxLengthExceededErrorMessage(DATA_UNIT_SCHEMA_NAME, DATA_UNIT_SCHEMA_NAME_MAX_LENGTH));
			}

			if ((id == null && schemaService.existsByName(name))
					|| (id != null && schemaService.existsByNameAndNotId(name, id))) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.buildFoundDuplicateErrorMessage(DATA_UNIT_SCHEMA_NAME, name));
			}
		}

		final DataUnitPropertySchemaValidationContext context;
		if (id != null) {
			context = schemaService.findById(id).
					map(DataUnitPropertySchemaValidationContext::of).
					orElseGet(DataUnitPropertySchemaValidationContext::empty);
		} else {
			context = DataUnitPropertySchemaValidationContext.empty();
		}

		final List<DataUnitPropertySchemaApiModel> propertySchemas = schema.getPropertySchemas();
		if (CollectionUtils.isEmpty(propertySchemas)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsEmptyErrorMessage("dataUnitSchema.propertySchemas"));
		} else {
			propertySchemas.forEach(propertySchema ->
					validatePropertySchema(context, propertySchema, result));
		}

		return result;
	}

	private void validatePropertySchema(final @NonNull DataUnitPropertySchemaValidationContext context,
										final @Nullable DataUnitPropertySchemaApiModel propertySchema,
										final @NonNull ValidationResult result) {
		if (propertySchema == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsNullErrorMessage("dataUnitSchema.propertySchema"));
		} else {
			final Long propertySchemaId = propertySchema.getId();
			if (propertySchemaId != null
					&& (context.getSchemaId() == null || !context.isValidPropertySchemaId(propertySchemaId))) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.
						buildNotFoundByIdErrorMessage("dataUnitPropertySchema", propertySchemaId));
			}

			validatePropertySchemaName(context, propertySchema.getName(), result);

			if (propertySchema.getType() == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.
						buildIsNullErrorMessage("dataUnitSchema.propertySchema.type"));
			}
		}
	}


	private void validatePropertySchemaName(final @NonNull DataUnitPropertySchemaValidationContext context,
											final @Nullable String name, final @NonNull ValidationResult result) {
		if (StringUtils.isBlank(name)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsBlankErrorMessage(DATA_UNIT_PROPERTY_SCHEMA_NAME));
		} else {
			if (name.length() > DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.
						buildMaxLengthExceededErrorMessage(DATA_UNIT_PROPERTY_SCHEMA_NAME,
								DATA_UNIT_SCHEMA_NAME_MAX_LENGTH));
			}

			if (!context.isUniquePropertySchemaName(name)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.
						buildFoundDuplicateErrorMessage(DATA_UNIT_PROPERTY_SCHEMA_NAME, name));
			}
		}
	}

	private static class DataUnitPropertySchemaValidationContext {

		private final Long schemaId;

		private final Set<Long> validPropertySchemaIds;

		private final Set<String> uniquePropertySchemaNames = new HashSet<>();

		private DataUnitPropertySchemaValidationContext(final @Nullable Long schemaId,
														final @NonNull Set<Long> validPropertySchemaIds) {
			this.schemaId = schemaId;
			this.validPropertySchemaIds = validPropertySchemaIds;
		}

		public static DataUnitPropertySchemaValidationContext of(final @NonNull DataUnitSchemaServiceModel schema) {
			return new DataUnitPropertySchemaValidationContext(schema.getId(), schema.getPropertySchemas().stream().
					filter(Objects::nonNull).
					map(Identified::getId).
					collect(Collectors.toSet()));
		}

		public static DataUnitPropertySchemaValidationContext empty() {
			return new DataUnitPropertySchemaValidationContext(null, new HashSet<>());
		}

		public Long getSchemaId() {
			return schemaId;
		}

		public boolean isValidPropertySchemaId(final @NonNull Long propertySchemaId) {
			return validPropertySchemaIds.contains(propertySchemaId);
		}

		public boolean isUniquePropertySchemaName(final @NonNull String propertySchemaName) {
			return uniquePropertySchemaNames.add(propertySchemaName);
		}
	}
}
