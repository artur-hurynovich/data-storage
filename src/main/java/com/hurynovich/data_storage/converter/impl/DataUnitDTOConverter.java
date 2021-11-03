package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataUnitDTOConverter implements DTOConverter<DataUnitDTO, DataUnitDocument, String> {

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public DataUnitDocument convert(final @Nullable DataUnitDTO source) {
		final DataUnitDocument target;
		if (source != null) {
			target = modelMapper.map(source, DataUnitDocument.class);
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitDTO convertBase(final @Nullable DataUnitDocument source) {
		final DataUnitDTO target;
		if (source != null) {
			target = new DataUnitDTO(source.getId(), source.getSchemaId(), new ArrayList<>());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitDTO convertFull(final @Nullable DataUnitDocument source) {
		final DataUnitDTO target;
		if (source != null) {
			target = new DataUnitDTO(source.getId(), source.getSchemaId(),
					MassProcessingUtils.processQuietly(source.getProperties(), this::convertProperty));
		} else {
			target = null;
		}

		return target;
	}

	private DataUnitPropertyDTO convertProperty(final @Nullable DataUnitPropertyDocument source) {
		final DataUnitPropertyDTO target;
		if (source != null) {
			target = new DataUnitPropertyDTO(source.getSchemaId(), source.getValue());
		} else {
			target = null;
		}

		return target;
	}

}
