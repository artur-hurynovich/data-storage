package com.hurynovich.data_storage.filter.model;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class FilterCriteria {

	private final Long schemaId;

	private final FilterCriteriaComparison comparison;

	private final Object comparisonPattern;

	public FilterCriteria(final @NonNull Long schemaId, final @NonNull FilterCriteriaComparison comparison,
						  final @NonNull Object comparisonPattern) {
		this.schemaId = Objects.requireNonNull(schemaId);
		this.comparison = Objects.requireNonNull(comparison);
		this.comparisonPattern = Objects.requireNonNull(comparisonPattern);
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public FilterCriteriaComparison getComparison() {
		return comparison;
	}

	public Object getComparisonPattern() {
		return comparisonPattern;
	}

}
