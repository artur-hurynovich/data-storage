package com.hurynovich.data_storage.service.paginator;

import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.model.PaginationParams;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface Paginator {

	PaginationParams buildParams(@Nullable Integer pageNumber, @NonNull Integer elementsPerPage);

	<T> GenericPage<T> buildPage(@NonNull List<T> elements, @NonNull Long totalElementsCount,
								 @NonNull PaginationParams params);

}
