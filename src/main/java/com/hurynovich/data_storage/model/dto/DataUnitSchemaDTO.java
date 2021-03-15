package com.hurynovich.data_storage.model.dto;

import java.util.List;

public class DataUnitSchemaDTO extends AbstractDTO<Long> {

	private String name;

	private List<DataUnitPropertySchemaDTO> propertySchemas;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<DataUnitPropertySchemaDTO> getPropertySchemas() {
		return propertySchemas;
	}

	public void setPropertySchemas(final List<DataUnitPropertySchemaDTO> propertySchemas) {
		this.propertySchemas = propertySchemas;
	}

}
