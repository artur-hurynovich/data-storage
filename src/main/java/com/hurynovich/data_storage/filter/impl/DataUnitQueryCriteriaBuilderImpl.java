package com.hurynovich.data_storage.filter.impl;

import com.hurynovich.data_storage.filter.DataUnitQueryCriteriaBuilder;
import com.hurynovich.data_storage.filter.exception.DataUnitQueryCriteriaBuilderException;
import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.filter.model.DataUnitPropertyCriteria;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyDocument_;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

class DataUnitQueryCriteriaBuilderImpl implements DataUnitQueryCriteriaBuilder {

	private final Map<CriteriaComparison, BiConsumer<Criteria, DataUnitPropertyCriteria>> valueCriteriaAppliersByComparison;

	public DataUnitQueryCriteriaBuilderImpl(
			final @NonNull Map<CriteriaComparison, BiConsumer<Criteria, DataUnitPropertyCriteria>> valueCriteriaAppliersByComparison) {
		this.valueCriteriaAppliersByComparison = Objects.requireNonNull(valueCriteriaAppliersByComparison);
	}

	@Override
	public Criteria build(final @NonNull DataUnitFilter filter) {
		final Criteria resultCriteria = buildSchemaIdCriteria(filter);
		final Criteria[] propertyCriteria = filter.getCriteria().stream().
				map(this::buildPropertyCriteria).
				toArray(Criteria[]::new);
		if (propertyCriteria.length > 0) {
			resultCriteria.andOperator(propertyCriteria);
		}

		return resultCriteria;
	}

	private Criteria buildSchemaIdCriteria(final @NonNull DataUnitFilter filter) {
		return Criteria.where(DataUnitDocument_.SCHEMA_ID).is(filter.getSchemaId());
	}

	private Criteria buildPropertyCriteria(final @NonNull DataUnitPropertyCriteria filterCriteria) {
		final Criteria elemMatchCriteria = Criteria.
				where(DataUnitPropertyDocument_.SCHEMA_ID).
				is(filterCriteria.getPropertySchemaId());
		applyValueCriteria(elemMatchCriteria, filterCriteria);

		return Criteria.
				where(DataUnitDocument_.PROPERTIES).
				elemMatch(elemMatchCriteria);
	}

	private void applyValueCriteria(final @NonNull Criteria elemMatchCriteria,
									final @NonNull DataUnitPropertyCriteria filterCriteria) {
		final CriteriaComparison comparison = filterCriteria.getComparison();
		final BiConsumer<Criteria, DataUnitPropertyCriteria> applier = valueCriteriaAppliersByComparison.get(comparison);
		if (applier != null) {
			applier.accept(elemMatchCriteria.and(DataUnitPropertyDocument_.VALUE), filterCriteria);
		} else {
			throw new DataUnitQueryCriteriaBuilderException("Value criteria applier for comparison = '" +
					comparison + "' not found");
		}
	}

}
