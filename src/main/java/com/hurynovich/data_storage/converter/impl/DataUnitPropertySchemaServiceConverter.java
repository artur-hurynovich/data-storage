package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class DataUnitPropertySchemaServiceConverter
		implements ServiceConverter<DataUnitPropertySchemaServiceModel, DataUnitPropertySchemaPersistentModel> {

	@Override
	public DataUnitPropertySchemaPersistentModel convert(final @Nullable DataUnitPropertySchemaServiceModel source,
														 final @Nullable String... ignoreProperties) {
		final DataUnitPropertySchemaEntity target;
		if (source != null) {
			target = new DataUnitPropertySchemaEntity();
			target.setId(source.getId());
			target.setName(source.getName());
			target.setType(source.getType());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitPropertySchemaServiceModel convert(final @Nullable DataUnitPropertySchemaPersistentModel source,
													  final @Nullable String... ignoreProperties) {
		final DataUnitPropertySchemaServiceModelImpl target;
		if (source != null) {
			target = new DataUnitPropertySchemaServiceModelImpl(source.getId(), source.getName(), source.getType());
		} else {
			target = null;
		}

		return target;
	}
}
