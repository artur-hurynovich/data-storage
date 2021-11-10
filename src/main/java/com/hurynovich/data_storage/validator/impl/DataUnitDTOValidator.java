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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataUnitDTOValidator implements DTOValidator<DataUnitDTO> {

	private final BaseDTOService<DataUnitSchemaDTO, Long> service;

	private final DTOValidationHelper helper;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitDTOValidator(final @NonNull BaseDTOService<DataUnitSchemaDTO, Long> service,
								final @NonNull DTOValidationHelper helper,
								final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.service = service;
		this.helper = helper;
		this.valueCheckProcessor = valueCheckProcessor;
	}

	@Override
	public ValidationResult validate(final @NonNull DataUnitDTO dataUnit) {
		final ValidationResult result = new ValidationResult();
		final Long schemaId = dataUnit.getSchemaId();
		if (schemaId == null) {
			helper.applyIsNullError("dataUnit.schemaId", result);
		} else {
			final Optional<DataUnitSchemaDTO> schemaOptional = service.findById(schemaId);
			if (schemaOptional.isEmpty()) {
				helper.applyNotFoundByIdError("dataUnitSchema", schemaId, result);
			} else {
				final List<DataUnitPropertyDTO> properties = dataUnit.getProperties();
				if (CollectionUtils.isEmpty(properties)) {
					helper.applyIsEmptyError("dataUnit.properties", result);
				} else {
					validateProperties(schemaOptional.get(), properties, result);
				}
			}
		}

		return result;
	}

	private void validateProperties(final @NonNull DataUnitSchemaDTO schema,
									final @NonNull List<DataUnitPropertyDTO> properties,
									final @NonNull ValidationResult result) {
		final Map<Long, DataUnitPropertySchemaDTO> propertySchemasById = schema.getPropertySchemas().stream().
				collect(Collectors.toMap(AbstractDTO::getId, Function.identity()));

		properties.forEach(property -> validateProperty(property, propertySchemasById, result));
	}

	private void validateProperty(final @Nullable DataUnitPropertyDTO property,
								  final @NonNull Map<Long, DataUnitPropertySchemaDTO> propertySchemasById,
								  final @NonNull ValidationResult result) {
		if (property == null) {
			helper.applyIsNullError("dataUnit.property", result);
		} else {
			final Long schemaId = property.getSchemaId();
			if (schemaId == null) {
				helper.applyIsNullError("dataUnit.property.schemaId", result);
			} else {
				final DataUnitPropertySchemaDTO propertySchema = propertySchemasById.get(schemaId);
				if (propertySchema == null) {
					helper.applyNotFoundByIdError("dataUnitPropertySchema", schemaId, result);
				} else {
					final Object value = property.getValue();
					if (!valueCheckProcessor.processCheck(propertySchema, value)) {
						result.setType(ValidationResultType.FAILURE);
						result.addError("'dataUnit.property.value' '" + value + "' is incorrect " +
								"for dataUnitProperty with schemaId = " + propertySchema.getId());
					}
				}
			}
		}
	}

}
