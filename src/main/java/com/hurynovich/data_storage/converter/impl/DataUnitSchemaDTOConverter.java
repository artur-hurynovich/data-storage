package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataUnitSchemaDTOConverter implements DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> {

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public DataUnitSchemaEntity convert(final @Nullable DataUnitSchemaDTO source) {
		final DataUnitSchemaEntity target;
		if (source != null) {
			target = modelMapper.map(source, DataUnitSchemaEntity.class);
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitSchemaDTO convertBase(final @Nullable DataUnitSchemaEntity source) {
		final DataUnitSchemaDTO target;
		if (source != null) {
			target = new DataUnitSchemaDTO(source.getId(), source.getName(), new ArrayList<>());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitSchemaDTO convertFull(final @Nullable DataUnitSchemaEntity source) {
		final DataUnitSchemaDTO target;
		if (source != null) {
			target = new DataUnitSchemaDTO(source.getId(), source.getName(),
					MassProcessingUtils.processQuietly(source.getPropertySchemas(), this::convertPropertySchema));
		} else {
			target = null;
		}

		return target;
	}

	private DataUnitPropertySchemaDTO convertPropertySchema(final @Nullable DataUnitPropertySchemaEntity source) {
		final DataUnitPropertySchemaDTO target;
		if (source != null) {
			target = new DataUnitPropertySchemaDTO(source.getId(), source.getName(), source.getType());
		} else {
			target = null;
		}

		return target;
	}

}
