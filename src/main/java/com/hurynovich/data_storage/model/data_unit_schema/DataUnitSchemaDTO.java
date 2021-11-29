package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class DataUnitSchemaDTO extends AbstractDTO<Long> {

	private final String name;

	private final List<DataUnitPropertySchemaDTO> propertySchemas;

	public DataUnitSchemaDTO(final Long id, final String name, final List<DataUnitPropertySchemaDTO> propertySchemas) {
		super(id);
		this.name = name;
		this.propertySchemas = CollectionUtils.isEmpty(propertySchemas) ?
				Collections.emptyList() : Collections.unmodifiableList(propertySchemas);
	}

	public String getName() {
		return name;
	}

	public List<DataUnitPropertySchemaDTO> getPropertySchemas() {
		return propertySchemas;
	}
}
