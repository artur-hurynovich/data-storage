package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.springframework.lang.NonNull;

public interface DataUnitSchemaDAO extends MassReadDAO<DataUnitSchemaEntity, Long> {

	boolean existsByName(@NonNull String name);

	boolean existsByNameAndNotId(@NonNull String name, @NonNull Long id);

}
