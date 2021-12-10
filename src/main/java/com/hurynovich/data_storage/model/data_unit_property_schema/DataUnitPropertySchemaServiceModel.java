package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.data_storage.model.ServiceModel;

public interface DataUnitPropertySchemaServiceModel extends ServiceModel<Long> {

	String getName();

	DataUnitPropertyType getType();
}
