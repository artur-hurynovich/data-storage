package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.GenerateMetamodel;
import com.hurynovich.data_storage.model.AbstractServiceModel;

@GenerateMetamodel
public class DataUnitPropertySchemaServiceModelImpl extends AbstractServiceModel<Long> implements DataUnitPropertySchemaServiceModel {

	private final String name;

	private final DataUnitPropertyType type;

	public DataUnitPropertySchemaServiceModelImpl(final Long id, final String name, final DataUnitPropertyType type) {
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
