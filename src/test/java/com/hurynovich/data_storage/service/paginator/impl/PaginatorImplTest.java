package com.hurynovich.data_storage.service.paginator.impl;

import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.service.paginator.Paginator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PaginatorImplTest {

	private static final int ELEMENTS_PER_PAGE = 5;

	private final Paginator paginator = new PaginatorImpl();

	@Test
	void buildParamsTest1() {
		final PaginationParams params = paginator.buildParams(null, ELEMENTS_PER_PAGE);

		Assertions.assertEquals(0, params.getOffset());
		Assertions.assertEquals(5, params.getLimit());
	}

	@Test
	void buildParamsTest2() {
		final PaginationParams params = paginator.buildParams(1, ELEMENTS_PER_PAGE);

		Assertions.assertEquals(0, params.getOffset());
		Assertions.assertEquals(5, params.getLimit());
	}

	@Test
	void buildParamsTest3() {
		final PaginationParams params = paginator.buildParams(5, ELEMENTS_PER_PAGE);

		Assertions.assertEquals(20, params.getOffset());
		Assertions.assertEquals(5, params.getLimit());
	}

	@Test
	void buildPageTest1() {
		final GenericPage<Object> page = paginator.buildPage(
				new ArrayList<>(), 27L, new PaginationParams(0, ELEMENTS_PER_PAGE));

		Assertions.assertEquals(27L, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertEquals(1, page.getCurrentPageNumber());
		Assertions.assertEquals(2, page.getNextPageNumber());
		Assertions.assertEquals(6, page.getTotalPagesCount());
	}

	@Test
	void buildPageTest2() {
		final GenericPage<Object> page = paginator.buildPage(
				new ArrayList<>(), 27L, new PaginationParams(10, ELEMENTS_PER_PAGE));

		Assertions.assertEquals(27L, page.getTotalElementsCount());
		Assertions.assertEquals(2, page.getPreviousPageNumber());
		Assertions.assertEquals(3, page.getCurrentPageNumber());
		Assertions.assertEquals(4, page.getNextPageNumber());
		Assertions.assertEquals(6, page.getTotalPagesCount());
	}

	@Test
	void buildPageTest3() {
		final GenericPage<Object> page = paginator.buildPage(
				new ArrayList<>(), 27L, new PaginationParams(25, ELEMENTS_PER_PAGE));

		Assertions.assertEquals(27L, page.getTotalElementsCount());
		Assertions.assertEquals(5, page.getPreviousPageNumber());
		Assertions.assertEquals(6, page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(6, page.getTotalPagesCount());
	}

	@Test
	void buildPageTest4() {
		final GenericPage<Object> page = paginator.buildPage(
				new ArrayList<>(), 3L, new PaginationParams(0, ELEMENTS_PER_PAGE));

		Assertions.assertEquals(3L, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertEquals(1, page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(1, page.getTotalPagesCount());
	}

	@Test
	void buildPageTest5() {
		final GenericPage<Object> page = paginator.buildPage(
				new ArrayList<>(), 0L, new PaginationParams(0, ELEMENTS_PER_PAGE));

		Assertions.assertEquals(0L, page.getTotalElementsCount());
		Assertions.assertNull(page.getPreviousPageNumber());
		Assertions.assertNull(page.getCurrentPageNumber());
		Assertions.assertNull(page.getNextPageNumber());
		Assertions.assertEquals(0, page.getTotalPagesCount());
	}

}
