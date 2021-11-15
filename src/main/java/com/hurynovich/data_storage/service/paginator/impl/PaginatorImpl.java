package com.hurynovich.data_storage.service.paginator.impl;

import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.service.paginator.Paginator;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class PaginatorImpl implements Paginator {

	@Override
	public PaginationParams buildParams(final @Nullable Integer pageNumber, final @NonNull Integer elementsPerPage) {
		Objects.requireNonNull(elementsPerPage);

		final int nonNullPageNumber = (pageNumber != null) ? pageNumber : 1;

		return new PaginationParams((nonNullPageNumber - 1) * elementsPerPage, elementsPerPage);
	}

	@Override
	public <T> GenericPage<T> buildPage(final @NonNull List<T> elements, final @NonNull Long totalElementsCount,
										final @NonNull PaginationParams params) {
		Objects.requireNonNull(elements);
		Objects.requireNonNull(totalElementsCount);

		final Integer offset = params.getOffset();
		final Integer limit = params.getLimit();

		final Long currentPageNumber = calculateCurrentPageNumber(totalElementsCount, offset, limit);
		final Long totalPagesCount = calculateTotalPagesCount(totalElementsCount, limit);
		final Long previousPageNumber = calculatePreviousPageNumber(currentPageNumber);
		final Long nextPageNumber = calculateNextPageNumber(currentPageNumber, totalPagesCount);

		return GenericPage.
				builder(elements).
				withTotalElementsCount(totalElementsCount).
				withPreviousPageNumber(previousPageNumber).
				withCurrentPageNumber(currentPageNumber).
				withNextPageNumber(nextPageNumber).
				withTotalPagesCount(totalPagesCount).
				build();
	}

	private Long calculateCurrentPageNumber(final @NonNull Long totalElementsCount, final @NonNull Integer offset,
											final @NonNull Integer limit) {
		final Long currentPageNumber;
		if (totalElementsCount > 0) {
			currentPageNumber = (long) (offset / limit + 1);
		} else {
			currentPageNumber = null;
		}

		return currentPageNumber;
	}

	private Long calculateTotalPagesCount(final @NonNull Long totalElementsCount, final @NonNull Integer limit) {
		final long totalPagesCount;
		if (totalElementsCount % limit > 0) {
			totalPagesCount = totalElementsCount / limit + 1;
		} else {
			totalPagesCount = totalElementsCount / limit;
		}

		return totalPagesCount;
	}

	private Long calculatePreviousPageNumber(final @Nullable Long currentPageNumber) {
		final Long previousPageNumber;
		if (currentPageNumber == null || currentPageNumber.equals(1L)) {
			previousPageNumber = null;
		} else {
			previousPageNumber = currentPageNumber - 1;
		}

		return previousPageNumber;
	}

	private Long calculateNextPageNumber(final @Nullable Long currentPageNumber, final @NonNull Long totalPagesCount) {
		final Long nextPageNumber;
		if (currentPageNumber == null || currentPageNumber.equals(totalPagesCount)) {
			nextPageNumber = null;
		} else {
			nextPageNumber = currentPageNumber + 1;
		}

		return nextPageNumber;
	}

}
