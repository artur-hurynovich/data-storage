package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.data_storage.model.PersistentModel;

public interface DataUnitPropertySchemaPersistentModel extends PersistentModel<Long> {

	String getName();

	DataUnitPropertyType getType();
}
