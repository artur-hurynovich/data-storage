package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DataUnitService extends BaseService<DataUnitServiceModel, String> {

	List<DataUnitServiceModel> findAll(@NonNull PaginationParams params, @NonNull DataUnitFilter filter);

	long count(@NonNull DataUnitFilter filter);

	void deleteAllBySchemaId(@NonNull Long schemaId);
}
