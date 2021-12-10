package com.hurynovich.data_storage.model.data_unit_schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;

import java.util.List;

@JsonDeserialize(as = DataUnitSchemaApiModelImpl.class)
public interface DataUnitSchemaApiModel extends ApiModel<Long> {

	String getName();

	List<DataUnitPropertySchemaApiModel> getPropertySchemas();
}
