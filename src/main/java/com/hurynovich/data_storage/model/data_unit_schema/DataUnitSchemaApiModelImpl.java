package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class DataUnitSchemaApiModelImpl extends AbstractApiModel<Long> implements DataUnitSchemaApiModel {

	private final String name;

	private final List<DataUnitPropertySchemaApiModel> propertySchemas;

	public DataUnitSchemaApiModelImpl(final Long id, final String name,
									  final List<DataUnitPropertySchemaApiModel> propertySchemas) {
		super(id);
		this.name = name;
		this.propertySchemas = CollectionUtils.isEmpty(propertySchemas) ?
				Collections.emptyList() : Collections.unmodifiableList(propertySchemas);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<DataUnitPropertySchemaApiModel> getPropertySchemas() {
		return propertySchemas;
	}
}
