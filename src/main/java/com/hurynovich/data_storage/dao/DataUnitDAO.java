package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DataUnitDAO extends BaseDAO<DataUnitPersistentModel, String> {

	List<DataUnitPersistentModel> findAll(@NonNull PaginationParams params, @NonNull DataUnitFilter filter);

	long count(@NonNull DataUnitFilter filter);

	void deleteAllBySchemaId(@NonNull Long schemaId);
}
