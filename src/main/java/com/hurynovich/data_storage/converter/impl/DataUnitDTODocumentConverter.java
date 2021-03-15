package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.document.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.model.dto.DataUnitPropertyDTO;
import org.springframework.stereotype.Service;

@Service
public class DataUnitDTODocumentConverter extends GenericPersistenceConverter<DataUnitDTO, DataUnitDocument, String> {

	private static final String[] IGNORE_PROPERTIES = {"properties"};

	private final Converter<DataUnitPropertyDTO, DataUnitPropertyDocument> propertyConverter;

	public DataUnitDTODocumentConverter(
			final Converter<DataUnitPropertyDTO, DataUnitPropertyDocument> propertyConverter) {
		super(IGNORE_PROPERTIES);

		this.propertyConverter = propertyConverter;
	}

	@Override
	public DataUnitDocument convert(final DataUnitDTO source) {
		final DataUnitDocument result = super.convert(source);

		if (source != null && result != null) {
			result.setProperties(propertyConverter.convertAll(source.getProperties()));
		}

		return result;
	}

	@Override
	protected Class<DataUnitDocument> getResultClass() {
		return DataUnitDocument.class;
	}

}
