package com.hurynovich.data_storage.filter.impl;

import com.hurynovich.data_storage.filter.DataUnitQueryCriteriaBuilder;
import com.hurynovich.data_storage.filter.model.CriteriaComparison;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.filter.model.DataUnitPropertyCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class DataUnitQueryCriteriaBuilderImplTest {

	private static final String EXPECTED_QUERY =
			"Query: { \"schemaId\" : 1, \"$and\" : " +
					"[ { \"properties\" : { \"$elemMatch\" : { \"schemaId\" : 1, \"value\" : 100}}}, " +
					"{ \"properties\" : { \"$elemMatch\" : { \"schemaId\" : 2, \"value\" : { \"$gt\" : { \"$java\" : 2021-11-20 } } } } }, " +
					"{ \"properties\" : { \"$elemMatch\" : { \"schemaId\" : 3, \"value\" : { \"$regularExpression\" : { \"pattern\" : \"^Test\", \"options\" : \"i\"}}}}} ] }, " +
					"Fields: {}, " +
					"Sort: {}";

	private final DataUnitQueryCriteriaBuilder queryCriteriaBuilder = new FilterConfig().dataUnitQueryCriteriaBuilder();

	@Test
	void test() {
		final Criteria criteria = queryCriteriaBuilder.build(new DataUnitFilter(
				1L, Arrays.asList(
				new DataUnitPropertyCriteria(1L, CriteriaComparison.EQ, 100),
				new DataUnitPropertyCriteria(2L, CriteriaComparison.GT, LocalDate.of(2021, 11, 20)),
				new DataUnitPropertyCriteria(3L, CriteriaComparison.STARTS_WITH, "Test"))));
		final Query query = new Query(criteria);

		Assertions.assertEquals(EXPECTED_QUERY, query.toString());
	}
}
