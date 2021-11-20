package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
public class DataUnitPropertyCriteriaComparisonController {

	private final Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType;

	public DataUnitPropertyCriteriaComparisonController(
			final @NonNull Map<DataUnitPropertyType, Set<CriteriaComparison>> criteriaComparisonsByPropertyType) {
		this.criteriaComparisonsByPropertyType = Collections.unmodifiableMap(criteriaComparisonsByPropertyType);
	}

	@GetMapping("/criteriaComparisons")
	public ResponseEntity<Set<CriteriaComparison>> getCriteriaComparisons(
			final @RequestParam DataUnitPropertyType propertyType) {
		return ResponseEntity.ok(criteriaComparisonsByPropertyType.get(propertyType));
	}

}
