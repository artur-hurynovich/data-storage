package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class DataUnitSchemaDAOImpl implements DataUnitSchemaDAO {

	private final EntityManager entityManager;

	public DataUnitSchemaDAOImpl(final @NonNull EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public DataUnitSchemaEntity save(final @NonNull DataUnitSchemaEntity dataUnitSchema) {
		if (dataUnitSchema.getId() == null) {
			entityManager.persist(dataUnitSchema);
		} else {
			entityManager.merge(dataUnitSchema);
		}

		return dataUnitSchema;
	}

	@Override
	public Optional<DataUnitSchemaEntity> findById(final @NonNull Long id) {
		return Optional.ofNullable(entityManager.find(DataUnitSchemaEntity.class, id));
	}

	@Override
	public List<DataUnitSchemaEntity> findAll() {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		final Root<DataUnitSchemaEntity> root = criteriaQuery.from(DataUnitSchemaEntity.class);
		criteriaQuery.select(root);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void deleteById(final @NonNull Long id) {
		final Optional<DataUnitSchemaEntity> dataUnitSchemaOptional = findById(id);
		if (dataUnitSchemaOptional.isPresent()) {
			entityManager.remove(dataUnitSchemaOptional.get());
		} else {
			throw new EntityNotFoundException("'DataUnitSchemaEntity' with id = " + id + " not found");
		}
	}


	@Override
	public boolean existsByName(final @NonNull String name) {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		final Root<DataUnitSchemaEntity> root = criteriaQuery.from(DataUnitSchemaEntity.class);
		criteriaQuery.select(root).
				where(criteriaBuilder.equal(root.get("name"), name));

		return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
	}

	@Override
	public boolean existsByNameAndNotId(final @NonNull String name, final @NonNull Long id) {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		final Root<DataUnitSchemaEntity> root = criteriaQuery.from(DataUnitSchemaEntity.class);
		criteriaQuery.select(root).
				where(criteriaBuilder.
						and(
								criteriaBuilder.equal(root.get("name"), name),
								criteriaBuilder.notEqual(root.get("id"), id)));

		return !entityManager.createQuery(criteriaQuery).getResultList().isEmpty();
	}

}
