package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.DataUnitPropertyType;

public class DataUnitPropertySchemaDTO extends AbstractDTO<Long> {

	private final String name;

	private final DataUnitPropertyType type;

	public DataUnitPropertySchemaDTO(final Long id, final String name, final DataUnitPropertyType type) {
		super(id);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public DataUnitPropertyType getType() {
		return type;
	}

}
