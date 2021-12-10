package com.hurynovich.data_storage.model.data_unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = DataUnitPropertyApiModelImpl.class)
public interface DataUnitPropertyApiModel {

	Long getSchemaId();

	Object getValue();
}
