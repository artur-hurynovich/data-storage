package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.BaseDTOService;
import com.hurynovich.data_storage.validator.DTOValidationHelper;
import com.hurynovich.data_storage.validator.DTOValidator;
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
public class DataUnitDTOValidator implements DTOValidator<DataUnitDTO> {

	private final BaseDTOService<DataUnitSchemaDTO, Long> schemaService;

	private final DTOValidationHelper helper;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitDTOValidator(final @NonNull BaseDTOService<DataUnitSchemaDTO, Long> schemaService,
								final @NonNull DTOValidationHelper helper,
								final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.schemaService = Objects.requireNonNull(schemaService);
		this.helper = Objects.requireNonNull(helper);
		this.valueCheckProcessor = Objects.requireNonNull(valueCheckProcessor);
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitDTO dataUnit) {
		final ValidationResult result = new ValidationResult();
		final Long schemaId = dataUnit.getSchemaId();
		if (schemaId == null) {
			helper.applyIsNullError("dataUnit.schemaId", result);
		} else {
			final Optional<DataUnitSchemaDTO> schemaOptional = schemaService.findById(schemaId);
			if (schemaOptional.isEmpty()) {
				helper.applyNotFoundByIdError("dataUnitSchema", schemaId, result);
			} else {
				final List<DataUnitPropertyDTO> properties = dataUnit.getProperties();
				if (CollectionUtils.isEmpty(properties)) {
					helper.applyIsEmptyError("dataUnit.properties", result);
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
			helper.applyIsNullError("dataUnit.property", result);
		} else {
			final Long propertySchemaId = property.getSchemaId();
			if (propertySchemaId == null) {
				helper.applyIsNullError("dataUnit.property.schemaId", result);
			} else {
				if (context.isValidPropertySchemaId(propertySchemaId)) {
					if (!context.isUniquePropertySchemaId(propertySchemaId)) {
						helper.applyFoundDuplicateError("dataUnit.property.schemaId", propertySchemaId, result);
					} else {
						validatePropertyValue(context.getPropertySchemaById(propertySchemaId),
								property.getValue(), result);
					}
				} else {
					helper.applyNotFoundByIdError("dataUnitPropertySchema", propertySchemaId, result);
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
