package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ApiModel;

@JsonDeserialize(as = DataUnitPropertySchemaApiModelImpl.class)
public interface DataUnitPropertySchemaApiModel extends ApiModel<Long> {

	String getName();

	DataUnitPropertyType getType();
}
