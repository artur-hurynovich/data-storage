package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaEntityConverter
		extends AbstractConverter<DataUnitSchemaEntity, DataUnitSchemaDTO> {

	public DataUnitSchemaEntityConverter() {
		super(DataUnitSchemaEntity.class, DataUnitSchemaDTO.class);
	}

}
