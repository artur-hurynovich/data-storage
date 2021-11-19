package com.hurynovich.data_storage.filter.model;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class DataUnitPropertyCriteria {

	private final Long propertySchemaId;

	private final CriteriaComparison comparison;

	private final Object comparisonPattern;

	public DataUnitPropertyCriteria(final @NonNull Long propertySchemaId,
									final @NonNull CriteriaComparison comparison,
									final @NonNull Object comparisonPattern) {
		this.propertySchemaId = Objects.requireNonNull(propertySchemaId);
		this.comparison = Objects.requireNonNull(comparison);
		this.comparisonPattern = Objects.requireNonNull(comparisonPattern);
	}

	public Long getPropertySchemaId() {
		return propertySchemaId;
	}

	public CriteriaComparison getComparison() {
		return comparison;
	}

	public Object getComparisonPattern() {
		return comparisonPattern;
	}

}
