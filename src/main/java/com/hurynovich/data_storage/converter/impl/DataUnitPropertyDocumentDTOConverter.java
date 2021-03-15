package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.document.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.dto.DataUnitPropertyDTO;
import org.springframework.stereotype.Service;

@Service
public class DataUnitPropertyDocumentDTOConverter
		extends GenericPersistenceConverter<DataUnitPropertyDocument, DataUnitPropertyDTO, String> {

	@Override
	protected Class<DataUnitPropertyDTO> getResultClass() {
		return DataUnitPropertyDTO.class;
	}

}
