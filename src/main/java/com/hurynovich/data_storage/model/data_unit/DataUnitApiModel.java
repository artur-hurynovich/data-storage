package com.hurynovich.data_storage.model.data_unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ApiModel;

import java.util.List;

@JsonDeserialize(as = DataUnitApiModelImpl.class)
public interface DataUnitApiModel extends ApiModel<String> {

	Long getSchemaId();

	List<DataUnitPropertyApiModel> getProperties();
}
