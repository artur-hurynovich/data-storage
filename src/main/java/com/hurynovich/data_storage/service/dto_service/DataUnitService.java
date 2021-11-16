package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import org.springframework.lang.NonNull;

public interface DataUnitService extends BaseService<DataUnitDTO, String> {

	void deleteAllBySchemaId(@NonNull Long schemaId);

}
