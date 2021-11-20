package com.hurynovich.data_storage.controller;

import com.hurynovich.data_storage.filter.impl.DataUnitPropertyCriteriaConfig;
import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

class DataUnitPropertyCriteriaComparisonControllerTest {

	private final DataUnitPropertyCriteriaComparisonController controller =
			new DataUnitPropertyCriteriaComparisonController(
					new DataUnitPropertyCriteriaConfig().criteriaComparisonsByPropertyType());

	@Test
	void test() {
		processTest(DataUnitPropertyType.TEXT, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.STARTS_WITH,
				CriteriaComparison.ENDS_WITH,
				CriteriaComparison.CONTAINS));

		processTest(DataUnitPropertyType.INTEGER, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));

		processTest(DataUnitPropertyType.FLOAT, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));

		processTest(DataUnitPropertyType.BOOLEAN, Set.of(
				CriteriaComparison.EQ));

		processTest(DataUnitPropertyType.DATE, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));

		processTest(DataUnitPropertyType.TIME, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));
	}

	private void processTest(final DataUnitPropertyType type, final Set<CriteriaComparison> expectedComparisons) {
		final ResponseEntity<Set<CriteriaComparison>> textCriteriaComparisonsResponse = controller.
				getCriteriaComparisons(type);
		Assertions.assertEquals(HttpStatus.OK, textCriteriaComparisonsResponse.getStatusCode());

		final Set<CriteriaComparison> actualComparisons = textCriteriaComparisonsResponse.getBody();
		Assertions.assertNotNull(actualComparisons);
		Assertions.assertEquals(expectedComparisons.size(), actualComparisons.size());
		Assertions.assertTrue(actualComparisons.containsAll(expectedComparisons));
	}

}
