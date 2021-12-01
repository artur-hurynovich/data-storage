package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
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
class DataUnitValidator implements Validator<DataUnitDTO> {

	private final BaseService<DataUnitSchemaDTO, Long> schemaService;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitValidator(final @NonNull BaseService<DataUnitSchemaDTO, Long> schemaService,
							 final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.schemaService = Objects.requireNonNull(schemaService);
		this.valueCheckProcessor = Objects.requireNonNull(valueCheckProcessor);
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitDTO dataUnit) {
		final ValidationResult result = new ValidationResult();
		final Long schemaId = dataUnit.getSchemaId();
		if (schemaId == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError(ValidationErrorMessageUtils.buildIsNullErrorMessage("dataUnit.schemaId"));
		} else {
			final Optional<DataUnitSchemaDTO> schemaOptional = schemaService.findById(schemaId);
			if (schemaOptional.isEmpty()) {
				result.setType(ValidationResultType.FAILURE);
				result.addError(ValidationErrorMessageUtils.buildNotFoundByIdErrorMessage("dataUnitSchema", schemaId));
			} else {
				final List<DataUnitPropertyDTO> properties = dataUnit.getProperties();
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
								  final @Nullable DataUnitPropertyDTO property,
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

	private void validatePropertyValue(final @NonNull DataUnitPropertySchemaDTO propertySchema,
									   final @NonNull Object value, final @NonNull ValidationResult result) {
		if (!valueCheckProcessor.processCheck(propertySchema, value)) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnit.property.value' '" + value + "' is incorrect " +
					"for dataUnitProperty with schemaId = " + propertySchema.getId());
		}
	}

	private static class DataUnitPropertyValidationContext {

		private final Map<Long, DataUnitPropertySchemaDTO> propertySchemasById;

		private final Set<Long> uniquePropertySchemaIds = new HashSet<>();

		private DataUnitPropertyValidationContext(final @NonNull Map<Long, DataUnitPropertySchemaDTO> propertySchemasById) {
			this.propertySchemasById = propertySchemasById;
		}

		public static DataUnitPropertyValidationContext of(final @NonNull DataUnitSchemaDTO schema) {
			return new DataUnitPropertyValidationContext(schema.getPropertySchemas().stream().
					collect(Collectors.toMap(AbstractDTO::getId, Function.identity())));
		}

		public boolean isValidPropertySchemaId(final @NonNull Long propertySchemaId) {
			return propertySchemasById.containsKey(propertySchemaId);
		}

		public boolean isUniquePropertySchemaId(final @NonNull Long propertySchemaId) {
			return uniquePropertySchemaIds.add(propertySchemaId);
		}

		public DataUnitPropertySchemaDTO getPropertySchemaById(final @NonNull Long propertySchemaId) {
			return propertySchemasById.get(propertySchemaId);
		}
	}
}
