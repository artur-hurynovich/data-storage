package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;

public interface DataUnitSchemaDAO extends DAO<DataUnitSchemaEntity, Long> {

	boolean existsById(Long id);

}
