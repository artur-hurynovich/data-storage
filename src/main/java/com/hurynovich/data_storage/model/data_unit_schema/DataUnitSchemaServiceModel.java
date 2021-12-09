package com.hurynovich.data_storage.model.data_unit_schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;

import java.util.List;

@JsonDeserialize(as = DataUnitSchemaServiceModelImpl.class)
public interface DataUnitSchemaServiceModel extends ServiceModel<Long> {

	String getName();

	List<DataUnitPropertySchemaServiceModel> getPropertySchemas();
}
