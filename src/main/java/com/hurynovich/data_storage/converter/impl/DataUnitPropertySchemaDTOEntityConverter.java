package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitPropertySchemaDTOEntityConverter
		extends GenericPersistenceConverter<DataUnitPropertySchemaDTO, DataUnitPropertySchemaEntity, Long> {

	@Override
	protected Class<DataUnitPropertySchemaEntity> getResultClass() {
		return DataUnitPropertySchemaEntity.class;
	}

}
