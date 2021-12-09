package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.PersistentModel;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

public interface MassReadDAO<T extends PersistentModel<I>, I extends Serializable> extends BaseDAO<T, I> {

	List<T> findAll(@NonNull PaginationParams params);

	long count();
}
