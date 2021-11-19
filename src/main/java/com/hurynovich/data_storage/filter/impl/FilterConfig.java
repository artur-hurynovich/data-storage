package com.hurynovich.data_storage.filter.impl;

import com.hurynovich.data_storage.filter.DataUnitQueryCriteriaBuilder;
import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.filter.model.DataUnitPropertyCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Configuration
public class FilterConfig {

	private static final String START_OF_LINE_REGEX = "^";

	private static final String END_OF_LINE_REGEX = "$";

	private static final String ANY_SYMBOLS_REGEX = ".*";

	private static final String CASE_INSENSITIVE_OPTION = "i";

	@Bean
	public DataUnitQueryCriteriaBuilder dataUnitQueryCriteriaBuilder() {
		final Map<CriteriaComparison, BiConsumer<Criteria, DataUnitPropertyCriteria>> valueCriteriaAppliersByComparison =
				new EnumMap<>(CriteriaComparison.class);
		valueCriteriaAppliersByComparison.put(CriteriaComparison.EQ, (criteria, filterCriteria) ->
				criteria.is(filterCriteria.getComparisonPattern()));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.GT, (criteria, filterCriteria) ->
				criteria.gt(filterCriteria.getComparisonPattern()));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.GE, (criteria, filterCriteria) ->
				criteria.gte(filterCriteria.getComparisonPattern()));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.LT, (criteria, filterCriteria) ->
				criteria.lt(filterCriteria.getComparisonPattern()));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.LE, (criteria, filterCriteria) ->
				criteria.lte(filterCriteria.getComparisonPattern()));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.STARTS_WITH, (criteria, filterCriteria) ->
				criteria.regex(START_OF_LINE_REGEX + filterCriteria.getComparisonPattern(), CASE_INSENSITIVE_OPTION));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.ENDS_WITH, (criteria, filterCriteria) ->
				criteria.regex(filterCriteria.getComparisonPattern() + END_OF_LINE_REGEX, CASE_INSENSITIVE_OPTION));
		valueCriteriaAppliersByComparison.put(CriteriaComparison.CONTAINS, (criteria, filterCriteria) ->
				criteria.regex(ANY_SYMBOLS_REGEX + filterCriteria.getComparisonPattern() + ANY_SYMBOLS_REGEX, CASE_INSENSITIVE_OPTION));

		return new DataUnitQueryCriteriaBuilderImpl(valueCriteriaAppliersByComparison);
	}

}
