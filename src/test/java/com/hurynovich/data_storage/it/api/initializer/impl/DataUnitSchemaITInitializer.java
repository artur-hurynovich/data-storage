package com.hurynovich.data_storage.it.api.initializer.impl;

import com.hurynovich.data_storage.it.api.initializer.ITInitializer;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class DataUnitSchemaITInitializer implements ITInitializer<DataUnitSchemaEntity> {

	private static final String DELETE_SCHEMAS_QUERY = "DELETE FROM DataUnitSchemaEntity s";

	private static final String DELETE_PROPERTY_SCHEMAS_QUERY = "DELETE FROM DataUnitPropertySchemaEntity ps";

	private final EntityManager entityManager;

	public DataUnitSchemaITInitializer(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public DataUnitSchemaEntity init(final DataUnitSchemaEntity schema) {
		return entityManager.merge(schema);
	}

	@Override
	public void clear() {
		entityManager.createQuery(DELETE_PROPERTY_SCHEMAS_QUERY).executeUpdate();
		entityManager.createQuery(DELETE_SCHEMAS_QUERY).executeUpdate();
	}

}
