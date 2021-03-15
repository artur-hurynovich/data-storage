package com.hurynovich.data_storage.service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class DataUnitService extends GenericDTOService<DataUnitDTO, DataUnitDocument, String> {

	public DataUnitService(final PagingAndSortingRepository<DataUnitDocument, String> repository,
						   final Converter<DataUnitDTO, DataUnitDocument> converterToPersistent,
						   final Converter<DataUnitDocument, DataUnitDTO> converterFromPersistent) {
		super(repository, converterToPersistent, converterFromPersistent);
	}

}
