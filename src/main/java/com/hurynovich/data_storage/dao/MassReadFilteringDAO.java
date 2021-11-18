package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.filter.model.Filter;
import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.PaginationParams;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

public interface MassReadFilteringDAO<T extends Identified<I>, I extends Serializable> extends BaseDAO<T, I> {

	List<T> findAll(@NonNull PaginationParams params, @NonNull Filter filter);

	long count();

}
