package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaEntityDTOConverter
		extends GenericConverter<DataUnitSchemaEntity, DataUnitSchemaDTO, Long> {

	@Override
	protected Class<DataUnitSchemaDTO> getTargetClass() {
		return DataUnitSchemaDTO.class;
	}

}
