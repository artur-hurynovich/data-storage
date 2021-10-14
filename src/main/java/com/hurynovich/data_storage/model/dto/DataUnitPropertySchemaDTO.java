package com.hurynovich.data_storage.model.dto;

import com.hurynovich.data_storage.model.DataUnitPropertyType;

public class DataUnitPropertySchemaDTO extends AbstractDTO<Long> {

	private String name;

	private DataUnitPropertyType type;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public DataUnitPropertyType getType() {
		return type;
	}

	public void setType(final DataUnitPropertyType type) {
		this.type = type;
	}

}
