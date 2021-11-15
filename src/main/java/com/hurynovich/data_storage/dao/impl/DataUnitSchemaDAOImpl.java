package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import org.hibernate.annotations.QueryHints;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
class DataUnitSchemaDAOImpl implements DataUnitSchemaDAO {

	private final EntityManager entityManager;

	/*
	 * EntityManager is not thread-safe, however Spring injects not EntityManager itself,
	 * but proxy SharedEntityManagerCreator#SharedEntityManagerInvocationHandler. It
	 * delegates all calls to the current transactional EntityManager, if any; else, it
	 * will fall back to a newly created EntityManager per operation. So we don't need
	 * to care about thread safety. However, if we use DataUnitSchemaDAOImpl out of
	 * Spring IoC, it's constructor can be used with plain EntityManager, so thread-safety
	 * might be violated.
	 */
	public DataUnitSchemaDAOImpl(final @NonNull EntityManager entityManager) {
		this.entityManager = Objects.requireNonNull(entityManager);
	}

	@Override
	public DataUnitSchemaEntity save(final @NonNull DataUnitSchemaEntity dataUnitSchema) {
		return entityManager.merge(dataUnitSchema);
	}

	@Override
	public Optional<DataUnitSchemaEntity> findById(final @NonNull Long id) {
		return Optional.ofNullable(entityManager.find(DataUnitSchemaEntity.class, id));
	}

	@Override
	public void deleteById(final @NonNull Long id) {
		final DataUnitSchemaEntity dataUnitSchema = entityManager.find(DataUnitSchemaEntity.class, id);
		if (dataUnitSchema != null) {
			entityManager.remove(dataUnitSchema);
		} else {
			throw new EntityNotFoundException("'DataUnitSchemaEntity' with id = '" + id + "' not found");
		}
	}

	@Override
	public List<DataUnitSchemaEntity> findAll(final @NonNull PaginationParams params) {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		criteriaQuery.
				select(criteriaQuery.
						from(DataUnitSchemaEntity.class));

		return entityManager.
				createQuery(criteriaQuery).
				setFirstResult(params.getOffset()).
				setMaxResults(params.getLimit()).
				setHint(QueryHints.READ_ONLY, true).
				getResultList();
	}

	@Override
	public long count() {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.
				select(criteriaBuilder.
						count(criteriaQuery.
								from(DataUnitSchemaEntity.class)));

		return entityManager.
				createQuery(criteriaQuery).
				setHint(QueryHints.READ_ONLY, true).
				getSingleResult();
	}

	@Override
	public boolean existsByName(final @NonNull String name) {
		Objects.requireNonNull(name);

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		final Root<DataUnitSchemaEntity> root = criteriaQuery.from(DataUnitSchemaEntity.class);
		criteriaQuery.select(root).
				where(criteriaBuilder.equal(root.get(DataUnitSchemaEntity_.name), name));

		return !entityManager.
				createQuery(criteriaQuery).
				setHint(QueryHints.READ_ONLY, true).
				getResultList().
				isEmpty();
	}

	@Override
	public boolean existsByNameAndNotId(final @NonNull String name, final @NonNull Long id) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(id);

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DataUnitSchemaEntity> criteriaQuery = criteriaBuilder.
				createQuery(DataUnitSchemaEntity.class);
		final Root<DataUnitSchemaEntity> root = criteriaQuery.from(DataUnitSchemaEntity.class);
		criteriaQuery.select(root).
				where(criteriaBuilder.
						and(
								criteriaBuilder.
										equal(root.get(DataUnitSchemaEntity_.name), name),
								criteriaBuilder.
										notEqual(root.get(AbstractEntity_.id), id)));

		return !entityManager.
				createQuery(criteriaQuery).
				setHint(QueryHints.READ_ONLY, true).
				getResultList().
				isEmpty();
	}

}
