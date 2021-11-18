package com.hurynovich.data_storage.filter.impl;

import com.hurynovich.data_storage.filter.DataUnitQueryCriteriaBuilder;
import com.hurynovich.data_storage.filter.exception.DataUnitQueryCriteriaBuilderException;
import com.hurynovich.data_storage.filter.model.Filter;
import com.hurynovich.data_storage.filter.model.FilterCriteria;
import com.hurynovich.data_storage.filter.model.FilterCriteriaComparison;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyDocument_;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

class DataUnitQueryCriteriaBuilderImpl implements DataUnitQueryCriteriaBuilder {

	private final Map<FilterCriteriaComparison, BiConsumer<Criteria, FilterCriteria>> valueCriteriaAppliersByComparison;

	public DataUnitQueryCriteriaBuilderImpl(
			final @NonNull Map<FilterCriteriaComparison, BiConsumer<Criteria, FilterCriteria>> valueCriteriaAppliersByComparison) {
		this.valueCriteriaAppliersByComparison = Objects.requireNonNull(valueCriteriaAppliersByComparison);
	}

	@Override
	public Criteria build(final @NonNull Filter filter) {
		final Criteria resultCriteria = new Criteria();
		final Criteria[] criteria = filter.getCriteria().stream().
				map(this::buildSingleCriteria).
				toArray(Criteria[]::new);
		if (criteria.length > 0) {
			resultCriteria.andOperator(criteria);
		}

		return resultCriteria;
	}

	private Criteria buildSingleCriteria(final @NonNull FilterCriteria filterCriteria) {
		final Criteria elemMatchCriteria = Criteria.
				where(DataUnitPropertyDocument_.SCHEMA_ID).
				is(filterCriteria.getSchemaId());
		applyValueCriteria(elemMatchCriteria, filterCriteria);

		return Criteria.
				where(DataUnitDocument_.PROPERTIES).
				elemMatch(elemMatchCriteria);
	}

	private void applyValueCriteria(final @NonNull Criteria elemMatchCriteria,
									final @NonNull FilterCriteria filterCriteria) {
		final FilterCriteriaComparison comparison = filterCriteria.getComparison();
		final BiConsumer<Criteria, FilterCriteria> applier = valueCriteriaAppliersByComparison.get(comparison);
		if (applier != null) {
			applier.accept(elemMatchCriteria.and(DataUnitPropertyDocument_.VALUE), filterCriteria);
		} else {
			throw new DataUnitQueryCriteriaBuilderException("Value criteria applier for comparison = '" +
					comparison + "' not found");
		}
	}

}
