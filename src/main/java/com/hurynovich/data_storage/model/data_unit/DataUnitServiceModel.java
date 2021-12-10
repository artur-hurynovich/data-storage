package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.ServiceModel;

import java.util.List;

public interface DataUnitServiceModel extends ServiceModel<String> {

	Long getSchemaId();

	List<DataUnitPropertyServiceModel> getProperties();
}
