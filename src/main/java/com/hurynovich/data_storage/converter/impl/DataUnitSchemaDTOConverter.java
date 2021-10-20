package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DataUnitSchemaDTOConverter
		extends GenericDTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> {

	private final ModelMapper ignoringModelMapper;

	public DataUnitSchemaDTOConverter() {
		ignoringModelMapper = new ModelMapper();
		ignoringModelMapper.addMappings(new PropertyMap<DataUnitSchemaEntity, DataUnitSchemaDTO>() {
			@Override
			protected void configure() {
				skip().setPropertySchemas(null);
			}
		});
	}

	@Override
	protected Class<DataUnitSchemaEntity> getTargetClass() {
		return DataUnitSchemaEntity.class;
	}

	@Override
	protected Class<DataUnitSchemaDTO> getDTOClass() {
		return DataUnitSchemaDTO.class;
	}

	@Override
	public List<DataUnitSchemaDTO> convert(final Iterable<DataUnitSchemaEntity> sources) {
		final List<DataUnitSchemaDTO> targets = new ArrayList<>();
		if (sources != null) {
			targets.addAll(StreamSupport.stream(sources.spliterator(), false).
					filter(Objects::nonNull).
					map(this::ignoringConvert).
					collect(Collectors.toList()));
		}

		return targets;
	}

	private DataUnitSchemaDTO ignoringConvert(final DataUnitSchemaEntity source) {
		final DataUnitSchemaDTO target;
		if (source != null) {
			target = ignoringModelMapper.map(source, getDTOClass());
		} else {
			target = null;
		}

		return target;
	}

}
