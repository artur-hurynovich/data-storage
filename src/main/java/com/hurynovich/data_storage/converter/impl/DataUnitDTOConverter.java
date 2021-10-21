package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import org.springframework.stereotype.Service;

@Service
public class DataUnitDTOConverter extends AbstractConverter<DataUnitDTO, DataUnitDocument> {

	public DataUnitDTOConverter() {
		super(DataUnitDTO.class, DataUnitDocument.class);
	}

}
