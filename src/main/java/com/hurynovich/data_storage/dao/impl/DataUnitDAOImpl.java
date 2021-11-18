package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DataUnitDAO;
import com.hurynovich.data_storage.filter.DataUnitQueryCriteriaBuilder;
import com.hurynovich.data_storage.filter.model.Filter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
class DataUnitDAOImpl implements DataUnitDAO {

	private final MongoTemplate mongoTemplate;

	private final DataUnitQueryCriteriaBuilder criteriaBuilder;

	private final Query emptyQuery = new Query();

	public DataUnitDAOImpl(final @NonNull MongoTemplate mongoTemplate,
						   final @NonNull DataUnitQueryCriteriaBuilder criteriaBuilder) {
		this.mongoTemplate = Objects.requireNonNull(mongoTemplate);
		this.criteriaBuilder = criteriaBuilder;
	}

	@Override
	public DataUnitDocument save(final @NonNull DataUnitDocument dataUnit) {
		return mongoTemplate.save(dataUnit);
	}

	@Override
	public Optional<DataUnitDocument> findById(final @NonNull String id) {
		return Optional.ofNullable(mongoTemplate.findById(id, DataUnitDocument.class));
	}

	@Override
	public void delete(final @NonNull DataUnitDocument dataUnit) {
		mongoTemplate.remove(dataUnit);
	}

	@Override
	public List<DataUnitDocument> findAll(final @NonNull PaginationParams params, final @NonNull Filter filter) {
		final Query query = new Query().
				addCriteria(criteriaBuilder.build(filter)).
				skip(params.getOffset()).
				limit(params.getLimit());

		return mongoTemplate.find(query, DataUnitDocument.class);
	}

	@Override
	public long count() {
		return mongoTemplate.count(emptyQuery, DataUnitDocument.class);
	}

	@Override
	public void deleteAllBySchemaId(final @NonNull Long schemaId) {
		final Query query = new Query();
		query.addCriteria(Criteria.where(DataUnitDocument_.SCHEMA_ID).is(schemaId));
		mongoTemplate.findAndRemove(query, DataUnitDocument.class);
	}

}
