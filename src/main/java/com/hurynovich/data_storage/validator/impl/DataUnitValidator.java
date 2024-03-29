package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.BaseService;
import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import com.hurynovich.data_storage.validator.Validator;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import com.hurynovich.data_storage.validator.model.ValidationResultType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class DataUnitValidator implements Validator<DataUnitApiModel> {

	private final BaseService<DataUnitSchemaServiceModel, Long> schemaService;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitValidator(final @NonNull BaseService<DataUnitSchemaServiceModel, Long> schemaService,
							 final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.schemaService = Objects.requireNonNull(schemaService);
		this.valueCheckProcessor = Objects.requireNonNull(valueCheckProcessor);
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitApiModel dataUnit) {
		final ValidationResult result = new ValidationResult();
		final Long schemaId = dataUnit.getSchemaId();
		if (schemaId == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsNullErrorMessage("dataUnit.schemaId"));
		} else {
			final Optional<DataUnitSchemaServiceModel> schemaOptional = schemaService.findById(schemaId);
			if (schemaOptional.isEmpty()) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.buildNotFoundByIdErrorMessage("dataUnitSchema", schemaId));
			} else {
				final List<DataUnitPropertyApiModel> properties = dataUnit.getProperties();
				if (CollectionUtils.isEmpty(properties)) {
					result.setType(ValidationResultType.FAILURE);
					result.addError(ValidationErrorMessageUtils.buildIsEmptyErrorMessage("dataUnit.properties"));
				} else {
					final DataUnitPropertyValidationContext context = DataUnitPropertyValidationContext.
							of(schemaOptional.get());

					properties.forEach(property -> validateProperty(context, property, result));
				}
			}
		}

		return result;
	}

	private void validateProperty(final @NonNull DataUnitPropertyValidationContext context,
								  final @Nullable DataUnitPropertyApiModel property,
								  final @NonNull ValidationResult result) {
		if (property == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsNullErrorMessage("dataUnit.property"));
		} else {
			final Long propertySchemaId = property.getSchemaId();
			if (propertySchemaId == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.buildIsNullErrorMessage("dataUnit.property.schemaId"));
			} else {
				if (context.isValidPropertySchemaId(propertySchemaId)) {
					if (!context.isUniquePropertySchemaId(propertySchemaId)) {
						result.setType(ValidationResultType.FAILURE);
						result.addError(ValidationErrorMessageUtils.
								buildFoundDuplicateErrorMessage("dataUnit.property.schemaId", propertySchemaId));
					} else {
						validatePropertyValue(context.getPropertySchemaById(propertySchemaId),
								property.getValue(), result);
					}
				} else {
					result.setType(ValidationResultType.FAILURE);
					result.addError(ValidationErrorMessageUtils.
							buildNotFoundByIdErrorMessage("dataUnitPropertySchema", propertySchemaId));
				}
			}
		}
	}

	private void validatePropertyValue(final @NonNull DataUnitPropertySchemaServiceModel propertySchema,
									   final @NonNull Object value, final @NonNull ValidationResult result) {
		if (!valueCheckProcessor.processCheck(propertySchema, value)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnit.property.value' '" + value + "' is incorrect " +
					"for dataUnitProperty with schemaId = " + propertySchema.getId());
		}
	}

	private static class DataUnitPropertyValidationContext {

		private final Map<Long, DataUnitPropertySchemaServiceModel> propertySchemasById;

		private final Set<Long> uniquePropertySchemaIds = new HashSet<>();

		private DataUnitPropertyValidationContext(final @NonNull Map<Long, DataUnitPropertySchemaServiceModel> propertySchemasById) {
			this.propertySchemasById = propertySchemasById;
		}

		public static DataUnitPropertyValidationContext of(final @NonNull DataUnitSchemaServiceModel schema) {
			return new DataUnitPropertyValidationContext(schema.getPropertySchemas().stream().
					collect(Collectors.toMap(Identified::getId, Function.identity())));
		}

		public boolean isValidPropertySchemaId(final @NonNull Long propertySchemaId) {
			return propertySchemasById.containsKey(propertySchemaId);
		}

		public boolean isUniquePropertySchemaId(final @NonNull Long propertySchemaId) {
			return uniquePropertySchemaIds.add(propertySchemaId);
		}

		public DataUnitPropertySchemaServiceModel getPropertySchemaById(final @NonNull Long propertySchemaId) {
			return propertySchemasById.get(propertySchemaId);
		}
	}
}
