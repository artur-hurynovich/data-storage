package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;

@Service
public class DataUnitSchemaDTOEntityConverter
		extends GenericPersistenceConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> {

	private static final String[] IGNORE_PROPERTIES = {"propertySchemas"};

	private final Converter<DataUnitPropertySchemaDTO, DataUnitPropertySchemaEntity> propertySchemasConverter;

	public DataUnitSchemaDTOEntityConverter(
			final Converter<DataUnitPropertySchemaDTO, DataUnitPropertySchemaEntity> propertySchemasConverter) {
		super(IGNORE_PROPERTIES);

		this.propertySchemasConverter = propertySchemasConverter;
	}

	@Override
	public DataUnitSchemaEntity convert(final DataUnitSchemaDTO source) {
		final DataUnitSchemaEntity result = super.convert(source);

		if (source != null && result != null) {
			result.setPropertySchemas(propertySchemasConverter.convertAll(source.getPropertySchemas()));
		}

		return result;
	}

	@Override
	protected Class<DataUnitSchemaEntity> getResultClass() {
		return DataUnitSchemaEntity.class;
	}

}
