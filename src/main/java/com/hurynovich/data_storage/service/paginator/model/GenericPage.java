package com.hurynovich.data_storage.service.paginator.model;

import javax.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public class GenericPage<T> {

	private final List<T> elements;

	private Long totalElementsCount;

	private Long previousPageNumber;

	private Long currentPageNumber;

	private Long nextPageNumber;

	private Long totalPagesCount;

	private GenericPage(final List<T> elements) {
		this.elements = elements;
	}

	public List<T> getElements() {
		return elements;
	}

	public Long getTotalElementsCount() {
		return totalElementsCount;
	}

	private void setTotalElementsCount(final Long totalElementsCount) {
		this.totalElementsCount = totalElementsCount;
	}

	public Long getPreviousPageNumber() {
		return previousPageNumber;
	}

	private void setPreviousPageNumber(final Long previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}

	public Long getCurrentPageNumber() {
		return currentPageNumber;
	}

	private void setCurrentPageNumber(final Long currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public Long getNextPageNumber() {
		return nextPageNumber;
	}

	private void setNextPageNumber(final Long nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public Long getTotalPagesCount() {
		return totalPagesCount;
	}

	private void setTotalPagesCount(final Long totalPagesCount) {
		this.totalPagesCount = totalPagesCount;
	}

	public static class GenericPageBuilder<T> {

		private final GenericPage<T> page;

		private GenericPageBuilder(final List<T> elements) {
			this.page = new GenericPage<>(elements);
		}

		public GenericPageBuilder<T> withTotalElementsCount(final Long totalElementsCount) {
			page.setTotalElementsCount(totalElementsCount);
			return this;
		}

		public GenericPageBuilder<T> withPreviousPageNumber(final Long previousPageNumber) {
			page.setPreviousPageNumber(previousPageNumber);
			return this;
		}

		public GenericPageBuilder<T> withCurrentPageNumber(final Long currentPageNumber) {
			page.setCurrentPageNumber(currentPageNumber);
			return this;
		}

		public GenericPageBuilder<T> withNextPageNumber(final Long nextPageNumber) {
			page.setNextPageNumber(nextPageNumber);
			return this;
		}

		public GenericPageBuilder<T> withTotalPagesCount(final Long totalPagesCount) {
			page.setTotalPagesCount(totalPagesCount);
			return this;
		}

		public GenericPage<T> build() {
			return page;
		}

	}

	public static <T> GenericPageBuilder<T> builder(final List<T> elements) {
		return new GenericPageBuilder<>(elements);
	}

}
