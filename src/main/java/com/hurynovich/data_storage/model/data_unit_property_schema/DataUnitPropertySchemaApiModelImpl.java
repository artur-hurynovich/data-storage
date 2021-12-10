package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.data_storage.model.AbstractApiModel;

public class DataUnitPropertySchemaApiModelImpl extends AbstractApiModel<Long>
		implements DataUnitPropertySchemaApiModel {

	private final String name;

	private final DataUnitPropertyType type;

	public DataUnitPropertySchemaApiModelImpl(final Long id, final String name, final DataUnitPropertyType type) {
		super(id);
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DataUnitPropertyType getType() {
		return type;
	}
}
