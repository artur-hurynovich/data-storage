package com.hurynovich.data_storage.validator.impl;

import com.hurynovich.data_storage.model.dto.AbstractDTO;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.service.data_unit_property_check_processor.DataUnitPropertyValueCheckProcessor;
import com.hurynovich.data_storage.service.dto_service.DTOService;
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
import java.util.stream.Collectors;

@Service
public class DataUnitDTOValidator implements DTOValidator<DataUnitDTO> {

	private final DTOService<DataUnitSchemaDTO, Long> service;

	private final DataUnitPropertyValueCheckProcessor valueCheckProcessor;

	public DataUnitDTOValidator(final @NonNull DTOService<DataUnitSchemaDTO, Long> service,
								final @NonNull DataUnitPropertyValueCheckProcessor valueCheckProcessor) {
		this.service = service;
		this.valueCheckProcessor = valueCheckProcessor;
	}

	@Override
	public ValidationResult validate(final @Nullable DataUnitDTO dataUnit) {
		final ValidationResult result = new ValidationResult();

		if (dataUnit == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnit' can't be null");
		} else {
			final Long schemaId = dataUnit.getSchemaId();
			if (schemaId == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnit.schemaId' can't be null");
			} else {
				final Optional<DataUnitSchemaDTO> schemaOptional = service.findById(schemaId);
				if (schemaOptional.isEmpty()) {
					result.setType(ValidationResultType.FAILURE);
					result.addError("'dataUnitSchema' with id = " + schemaId + " not found");
				} else {
					final List<DataUnitPropertyDTO> properties = dataUnit.getProperties();
					if (CollectionUtils.isEmpty(properties)) {
						result.setType(ValidationResultType.FAILURE);
						result.addError("'dataUnit.properties' can't be null or empty");
					} else {
						validateProperties(properties, schemaOptional.get(), result);
					}
				}
			}
		}

		return result;
	}

	private void validateProperties(final @NonNull List<DataUnitPropertyDTO> properties,
									final @NonNull DataUnitSchemaDTO schema,
									final @NonNull ValidationResult result) {
		final Map<Long, DataUnitPropertySchemaDTO> propertySchemasById = schema.getPropertySchemas().stream().
				collect(Collectors.toMap(AbstractDTO::getId, propertySchema -> propertySchema));

		properties.forEach(property -> validateProperty(property, propertySchemasById, result));
	}

	private void validateProperty(final @Nullable DataUnitPropertyDTO property,
								  final @NonNull Map<Long, DataUnitPropertySchemaDTO> propertySchemasById,
								  final @NonNull ValidationResult result) {
		if (property == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnit.property' can't be null");
		} else {
			validateNotNullProperty(property, propertySchemasById, result);
		}
	}

	private void validateNotNullProperty(final @NonNull DataUnitPropertyDTO property,
										 final @NonNull Map<Long, DataUnitPropertySchemaDTO> propertySchemasById,
										 final @NonNull ValidationResult result) {
		final Long schemaId = property.getSchemaId();
		if (schemaId == null) {
			result.setType(ValidationResultType.FAILURE);
			result.addError("'dataUnit.property.schemaId' can't be null");
		} else {
			final DataUnitPropertySchemaDTO propertySchema = propertySchemasById.get(schemaId);
			if (propertySchema == null) {
				result.setType(ValidationResultType.FAILURE);
				result.addError("'dataUnitPropertySchema' with id = " + schemaId + " not found");
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
