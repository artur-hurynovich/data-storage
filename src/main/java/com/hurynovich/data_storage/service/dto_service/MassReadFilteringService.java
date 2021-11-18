package com.hurynovich.data_storage.service.dto_service;

import com.hurynovich.data_storage.filter.model.Filter;
import com.hurynovich.data_storage.model.AbstractDTO;
import com.hurynovich.data_storage.model.PaginationParams;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

public interface MassReadFilteringService<T extends AbstractDTO<I>, I extends Serializable> extends BaseService<T, I> {

	List<T> findAll(@NonNull PaginationParams params, @NonNull Filter filter);

	long count();

}
