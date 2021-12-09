package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ServiceModel;

@JsonDeserialize(as = DataUnitPropertySchemaServiceModelImpl.class)
public interface DataUnitPropertySchemaServiceModel extends ServiceModel<Long> {

	String getName();

	DataUnitPropertyType getType();
}
