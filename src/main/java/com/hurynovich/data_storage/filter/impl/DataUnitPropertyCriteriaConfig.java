package com.hurynovich.data_storage.filter.impl;

import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class DataUnitPropertyCriteriaConfig {

	@Bean
	public Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType() {
		final Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType =
				new EnumMap<>(DataUnitPropertyType.class);
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.TEXT,
				Set.of(CriteriaComparison.EQ, CriteriaComparison.STARTS_WITH, CriteriaComparison.ENDS_WITH, CriteriaComparison.CONTAINS));
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.INTEGER,
				Set.of(CriteriaComparison.EQ, CriteriaComparison.GT, CriteriaComparison.GE, CriteriaComparison.LT, CriteriaComparison.LE));
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.FLOAT,
				Set.of(CriteriaComparison.EQ, CriteriaComparison.GT, CriteriaComparison.GE, CriteriaComparison.LT, CriteriaComparison.LE));
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.BOOLEAN,
				Set.of(CriteriaComparison.EQ));
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.DATE,
				Set.of(CriteriaComparison.EQ, CriteriaComparison.GT, CriteriaComparison.GE, CriteriaComparison.LT, CriteriaComparison.LE));
		criteriaComparisonsByPropertyType.put(DataUnitPropertyType.TIME,
				Set.of(CriteriaComparison.EQ, CriteriaComparison.GT, CriteriaComparison.GE, CriteriaComparison.LT, CriteriaComparison.LE));

		return criteriaComparisonsByPropertyType;
	}

}
