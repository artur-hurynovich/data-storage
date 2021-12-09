package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

class DataUnitPropertyCriteriaComparisonIT extends AbstractAPITest {

	@Test
	void getCriteriaComparisonsTextTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.TEXT, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.STARTS_WITH,
				CriteriaComparison.ENDS_WITH,
				CriteriaComparison.CONTAINS));
	}

	@Test
	void getCriteriaComparisonsIntegerTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.INTEGER, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsFloatTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.FLOAT, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsBooleanTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.BOOLEAN, Set.of(
				CriteriaComparison.EQ));
	}

	@Test
	void getCriteriaComparisonsDateTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.DATE, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));
	}

	@Test
	void getCriteriaComparisonsTimeTypeTest() {
		processGetCriteriaComparisonsTest(DataUnitPropertyType.TIME, Set.of(
				CriteriaComparison.EQ,
				CriteriaComparison.GT,
				CriteriaComparison.GE,
				CriteriaComparison.LT,
				CriteriaComparison.LE));
	}

	private void processGetCriteriaComparisonsTest(final DataUnitPropertyType type,
												   final Set<CriteriaComparison> expectedResponseBody) {
		final ResponseEntity<Set<CriteriaComparison>> responseEntity = send(
				HttpMethod.GET,
				buildUrlTemplate(type),
				new ParameterizedTypeReference<>() {
				});

		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		final Set<CriteriaComparison> body = responseEntity.getBody();
		Assertions.assertNotNull(body);
		Assertions.assertEquals(expectedResponseBody.size(), body.size());
		Assertions.assertTrue(body.containsAll(expectedResponseBody));
	}

	private String buildUrlTemplate(final DataUnitPropertyType type) {
		return "/criteriaComparisons?propertyType=" + type;
	}
}
