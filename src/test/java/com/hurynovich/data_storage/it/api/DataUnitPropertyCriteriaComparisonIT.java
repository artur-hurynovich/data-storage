package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;

class DataUnitPropertyCriteriaComparisonIT extends AbstractAPITest {

	@Test
	void getCriteriaComparisonsTextTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.TEXT),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ,
						CriteriaComparison.STARTS_WITH,
						CriteriaComparison.ENDS_WITH,
						CriteriaComparison.CONTAINS));
	}

	@Test
	void getCriteriaComparisonsIntegerTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.INTEGER),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ,
						CriteriaComparison.GT,
						CriteriaComparison.GE,
						CriteriaComparison.LT,
						CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsFloatTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.FLOAT),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ,
						CriteriaComparison.GT,
						CriteriaComparison.GE,
						CriteriaComparison.LT,
						CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsBooleanTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.BOOLEAN),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ));
	}

	@Test
	void getCriteriaComparisonsDateTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.DATE),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ,
						CriteriaComparison.GT,
						CriteriaComparison.GE,
						CriteriaComparison.LT,
						CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsTimeTypeTest() {
		requestAsserter.assertRequest(HttpMethod.GET, buildUrlTemplate(DataUnitPropertyType.TIME),
				HttpStatus.OK, Set.of(
						CriteriaComparison.EQ,
						CriteriaComparison.GT,
						CriteriaComparison.GE,
						CriteriaComparison.LT,
						CriteriaComparison.LE));
	}

	private String buildUrlTemplate(final DataUnitPropertyType type) {
		return "/criteriaComparisons?propertyType=" + type;
	}

}
