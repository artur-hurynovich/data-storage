package com.hurynovich.data_storage.service.data_unit_property_check_processor;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;

public interface DataUnitPropertyValueCheckProcessor {

	boolean processCheck(DataUnitPropertySchemaDTO schema, Object value);

}
