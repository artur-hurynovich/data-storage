package com.hurynovich.data_storage.model.data_unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl.DataUnitPropertyServiceModelImpl;

@JsonDeserialize(as = DataUnitPropertyServiceModelImpl.class)
public interface DataUnitPropertyServiceModel {

	Long getSchemaId();

	Object getValue();
}
