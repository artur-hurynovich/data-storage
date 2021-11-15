package com.hurynovich.data_storage.model;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class PaginationParams {

	private final Integer offset;

	private final Integer limit;

	public PaginationParams(final @NonNull Integer offset, final @NonNull Integer limit) {
		this.offset = Objects.requireNonNull(offset);
		this.limit = Objects.requireNonNull(limit);
	}

	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}

}
