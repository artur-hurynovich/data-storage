package com.hurynovich.data_storage.model.data_unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.ServiceModel;

import java.util.List;

@JsonDeserialize(as = DataUnitServiceModelImpl.class)
public interface DataUnitServiceModel extends ServiceModel<String> {

	Long getSchemaId();

	List<DataUnitPropertyServiceModel> getProperties();
}
