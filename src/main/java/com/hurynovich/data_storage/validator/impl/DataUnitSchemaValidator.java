package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
import com.hurynovich.data_storage.validator.ValidationErrorMessageBuilder;
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
class DataUnitSchemaValidator implements Validator<DataUnitSchemaDTO> {

	private static final String DATA_UNIT_SCHEMA_NAME = "dataUnitSchema.name";

	private static final String DATA_UNIT_PROPERTY_SCHEMA_NAME = "dataUnitSchema.propertySchema.name";

	private static final int DATA_UNIT_SCHEMA_NAME_MAX_LENGTH = 25;

	private static final int DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH = DATA_UNIT_SCHEMA_NAME_MAX_LENGTH;

	private final ValidationErrorMessageBuilder errorMessageBuilder;

	private final DataUnitSchemaService schemaService;

	public DataUnitSchemaValidator(final @NonNull ValidationErrorMessageBuilder errorMessageBuilder,
								   final @NonNull DataUnitSchemaService schemaService) {
		this.errorMessageBuilder = Objects.requireNonNull(errorMessageBuilder);
		this.schemaService = Objects.requireNonNull(schemaService);
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitSchemaDTO schema) {
		final ValidationResult result = new ValidationResult();
		final Long id = schema.getId();
		final String name = schema.getName();
		if (StringUtils.isBlank(name)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(errorMessageBuilder.buildIsBlankErrorMessage(DATA_UNIT_SCHEMA_NAME));
		} else {
			if (name.length() > DATA_UNIT_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.
						buildMaxLengthExceededErrorMessage(DATA_UNIT_SCHEMA_NAME, DATA_UNIT_SCHEMA_NAME_MAX_LENGTH));
			}

			if ((id == null && schemaService.existsByName(name))
					|| (id != null && schemaService.existsByNameAndNotId(name, id))) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.buildFoundDuplicateErrorMessage(DATA_UNIT_SCHEMA_NAME, name));
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

		final List<DataUnitPropertySchemaDTO> propertySchemas = schema.getPropertySchemas();
		if (CollectionUtils.isEmpty(propertySchemas)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(errorMessageBuilder.buildIsEmptyErrorMessage("dataUnitSchema.propertySchemas"));
		} else {
			propertySchemas.forEach(propertySchema ->
					validatePropertySchema(context, propertySchema, result));
		}

		return result;
	}

	private void validatePropertySchema(final @NonNull DataUnitPropertySchemaValidationContext context,
										final @Nullable DataUnitPropertySchemaDTO propertySchema,
										final @NonNull ValidationResult result) {
		if (propertySchema == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(errorMessageBuilder.buildIsNullErrorMessage("dataUnitSchema.propertySchema"));
		} else {
			final Long propertySchemaId = propertySchema.getId();
			if (propertySchemaId != null
					&& (context.getSchemaId() == null || !context.isValidPropertySchemaId(propertySchemaId))) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.
						buildNotFoundByIdErrorMessage("dataUnitPropertySchema", propertySchemaId));
			}

			validatePropertySchemaName(context, propertySchema.getName(), result);

			if (propertySchema.getType() == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.
						buildIsNullErrorMessage("dataUnitSchema.propertySchema.type"));
			}
		}
	}


	private void validatePropertySchemaName(final @NonNull DataUnitPropertySchemaValidationContext context,
											final @Nullable String name, final @NonNull ValidationResult result) {
		if (StringUtils.isBlank(name)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(errorMessageBuilder.buildIsBlankErrorMessage(DATA_UNIT_PROPERTY_SCHEMA_NAME));
		} else {
			if (name.length() > DATA_UNIT_PROPERTY_SCHEMA_NAME_MAX_LENGTH) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.
						buildMaxLengthExceededErrorMessage(DATA_UNIT_PROPERTY_SCHEMA_NAME,
								DATA_UNIT_SCHEMA_NAME_MAX_LENGTH));
			}

			if (!context.isUniquePropertySchemaName(name)) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(errorMessageBuilder.
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

		public static DataUnitPropertySchemaValidationContext of(final @NonNull DataUnitSchemaDTO schema) {
			return new DataUnitPropertySchemaValidationContext(schema.getId(), schema.getPropertySchemas().stream().
					filter(Objects::nonNull).
					map(AbstractDTO::getId).
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
