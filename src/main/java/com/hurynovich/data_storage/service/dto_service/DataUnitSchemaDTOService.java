package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import org.springframework.lang.NonNull;

public interface DataUnitSchemaDTOService extends MassReadDTOService<DataUnitSchemaDTO, Long> {

	boolean existsByName(@NonNull String name);

	boolean existsByNameAndNotId(@NonNull String name, @NonNull Long id);

}
