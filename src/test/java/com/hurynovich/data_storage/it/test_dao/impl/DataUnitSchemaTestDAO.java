package com.hurynovich.data_storage.it.test_dao.impl;

import com.hurynovich.data_storage.it.test_dao.TestDAO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.Map;

@Repository
@Transactional
public class DataUnitSchemaTestDAO implements TestDAO<DataUnitSchemaPersistentModel, Long> {

	private static final String ENTITY_GRAPH_KEY = "javax.persistence.fetchgraph";

	private final EntityManager entityManager;

	public DataUnitSchemaTestDAO(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public DataUnitSchemaPersistentModel save(final DataUnitSchemaPersistentModel schema) {
		return entityManager.merge(schema);
	}

	@Override
	public DataUnitSchemaPersistentModel findById(final Long id) {
		final EntityGraph<DataUnitSchemaEntity> entityGraph = entityManager.
				createEntityGraph(DataUnitSchemaEntity.class);
		entityGraph.addAttributeNodes(DataUnitSchemaEntity_.PROPERTY_SCHEMAS);

		return entityManager.find(DataUnitSchemaEntity.class, id, Map.of(ENTITY_GRAPH_KEY, entityGraph));
	}

	@Override
	public void deleteById(final Long id) {
		entityManager.remove(entityManager.find(DataUnitSchemaEntity.class, id));
	}
}
