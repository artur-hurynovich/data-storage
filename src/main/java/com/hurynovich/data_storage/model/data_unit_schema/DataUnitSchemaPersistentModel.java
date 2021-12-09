package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.PersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;

import java.util.List;

public interface DataUnitSchemaPersistentModel extends PersistentModel<Long> {

	String getName();

	List<DataUnitPropertySchemaPersistentModel> getPropertySchemas();
}
