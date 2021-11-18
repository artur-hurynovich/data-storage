package com.hurynovich.data_storage.filter;

import com.hurynovich.data_storage.filter.model.Filter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.NonNull;

public interface DataUnitQueryCriteriaBuilder {

	Criteria build(@NonNull Filter filter);

}
