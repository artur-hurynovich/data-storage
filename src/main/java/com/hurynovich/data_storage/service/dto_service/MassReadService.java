package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.ServiceModel;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

public interface MassReadService<T extends ServiceModel<I>, I extends Serializable> extends BaseService<T, I> {

	List<T> findAll(@NonNull PaginationParams params);

	long count();
}
