package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.document.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.dto.DataUnitPropertyDTO;
import org.springframework.stereotype.Service;

@Service
public class DataUnitPropertyDTODocumentConverter
		extends GenericPersistenceConverter<DataUnitPropertyDTO, DataUnitPropertyDocument, String> {

	@Override
	protected Class<DataUnitPropertyDocument> getResultClass() {
		return DataUnitPropertyDocument.class;
	}

}
