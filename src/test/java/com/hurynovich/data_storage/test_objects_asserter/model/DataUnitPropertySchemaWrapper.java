package com.hurynovich.data_storage.test_objects_asserter.model;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;

public class DataUnitPropertySchemaWrapper implements Identified<Long> {

	private final Long id;

	private final String name;

	private final DataUnitPropertyType type;

	private DataUnitPropertySchemaWrapper(final Long id, final String name, final DataUnitPropertyType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public static DataUnitPropertySchemaWrapper of(final DataUnitPropertySchemaEntity schema) {
		return new DataUnitPropertySchemaWrapper(schema.getId(), schema.getName(), schema.getType());
	}

	public static DataUnitPropertySchemaWrapper of(final DataUnitPropertySchemaDTO schema) {
		return new DataUnitPropertySchemaWrapper(schema.getId(), schema.getName(), schema.getType());
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public DataUnitPropertyType getType() {
		return type;
	}

}
