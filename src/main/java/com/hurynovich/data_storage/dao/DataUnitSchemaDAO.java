package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.lang.NonNull;

public interface DataUnitSchemaDAO extends DAO<DataUnitSchemaEntity, Long> {

	boolean existsByName(@NonNull String name);

	boolean existsByNameAndNotId(@NonNull String name, @NonNull Long id);

}
