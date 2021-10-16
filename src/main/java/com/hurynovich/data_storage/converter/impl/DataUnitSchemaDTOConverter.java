package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaDTOConverter
		extends GenericDTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> {

	@Override
	protected Class<DataUnitSchemaEntity> getTargetClass() {
		return DataUnitSchemaEntity.class;
	}

	@Override
	protected Class<DataUnitSchemaDTO> getDTOClass() {
		return DataUnitSchemaDTO.class;
	}

}
