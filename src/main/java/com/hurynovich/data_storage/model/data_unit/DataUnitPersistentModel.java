package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.PersistentModel;

import java.util.List;

public interface DataUnitPersistentModel extends PersistentModel<String> {

	Long getSchemaId();

	List<DataUnitPropertyPersistentModel> getProperties();
}
