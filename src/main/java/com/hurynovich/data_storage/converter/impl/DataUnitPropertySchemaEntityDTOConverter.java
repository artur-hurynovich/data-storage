package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitPropertySchemaEntityDTOConverter
		extends GenericConverter<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO, Long> {

	@Override
	protected Class<DataUnitPropertySchemaDTO> getTargetClass() {
		return DataUnitPropertySchemaDTO.class;
	}

}
