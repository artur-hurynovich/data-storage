package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.document.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitPropertyDTO;
import org.springframework.stereotype.Service;

@Service
public class DataUnitDocumentDTOConverter extends GenericPersistenceConverter<DataUnitDocument, DataUnitDTO, String> {

	private static final String[] IGNORE_PROPERTIES = {"properties"};

	private final Converter<DataUnitPropertyDocument, DataUnitPropertyDTO> propertyConverter;

	public DataUnitDocumentDTOConverter(
			final Converter<DataUnitPropertyDocument, DataUnitPropertyDTO> propertyConverter) {
		super(IGNORE_PROPERTIES);

		this.propertyConverter = propertyConverter;
	}

	@Override
	public DataUnitDTO convert(final DataUnitDocument source) {
		final DataUnitDTO result = super.convert(source);

		if (source != null && result != null) {
			result.setProperties(propertyConverter.convertAll(source.getProperties()));
		}

		return result;
	}

	@Override
	protected Class<DataUnitDTO> getResultClass() {
		return DataUnitDTO.class;
	}

}
