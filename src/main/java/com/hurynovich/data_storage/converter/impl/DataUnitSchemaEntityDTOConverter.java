package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaEntityDTOConverter
		extends GenericPersistenceConverter<DataUnitSchemaEntity, DataUnitSchemaDTO, Long> {

	private static final String[] IGNORE_PROPERTIES = {"propertySchemas"};

	private final Converter<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO> propertySchemasConverter;

	public DataUnitSchemaEntityDTOConverter(
			final Converter<DataUnitPropertySchemaEntity, DataUnitPropertySchemaDTO> propertySchemasConverter) {
		super(IGNORE_PROPERTIES);

		this.propertySchemasConverter = propertySchemasConverter;
	}

	@Override
	public DataUnitSchemaDTO convert(final DataUnitSchemaEntity source) {
		final DataUnitSchemaDTO result = super.convert(source);

		if (source != null && result != null) {
			result.setPropertySchemas(propertySchemasConverter.convertAll(source.getPropertySchemas()));
		}

		return result;
	}

	@Override
	protected Class<DataUnitSchemaDTO> getResultClass() {
		return DataUnitSchemaDTO.class;
	}

}
