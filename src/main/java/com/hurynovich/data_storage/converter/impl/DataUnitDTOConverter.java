package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import org.springframework.stereotype.Service;

@Service
public class DataUnitDTOConverter extends GenericDTOConverter<DataUnitDTO, DataUnitDocument> {

	@Override
	protected Class<DataUnitDocument> getTargetClass() {
		return DataUnitDocument.class;
	}

	@Override
	protected Class<DataUnitDTO> getDTOClass() {
		return DataUnitDTO.class;
	}

}
