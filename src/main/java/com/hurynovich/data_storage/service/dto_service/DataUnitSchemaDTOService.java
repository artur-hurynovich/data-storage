package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;

public interface DataUnitSchemaDTOService extends DTOService<DataUnitSchemaDTO, Long> {

	boolean existsById(Long id);

}
