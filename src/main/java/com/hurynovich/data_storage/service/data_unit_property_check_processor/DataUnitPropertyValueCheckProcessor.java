package com.hurynovich.data_storage.service.data_unit_property_check_processor;

import com.hurynovich.data_storage.model.dto.DataUnitPropertySchemaDTO;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface DataUnitPropertyValueCheckProcessor {

	boolean processCheck(@NonNull DataUnitPropertySchemaDTO propertySchema, @Nullable Object value);

}
