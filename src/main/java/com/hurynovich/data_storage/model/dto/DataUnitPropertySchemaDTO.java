package com.hurynovich.data_storage.model.dto;

import com.hurynovich.data_storage.model.DataPropertyType;

public class DataUnitPropertySchemaDTO extends AbstractDTO<Long> {

	private String name;

	private DataPropertyType type;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public DataPropertyType getType() {
		return type;
	}

	public void setType(final DataPropertyType type) {
		this.type = type;
	}

}
