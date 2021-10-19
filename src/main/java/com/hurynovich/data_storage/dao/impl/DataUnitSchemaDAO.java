package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataUnitSchemaDAO implements DAO<DataUnitSchemaEntity, Long> {

	private final JpaRepository<DataUnitSchemaEntity, Long> repository;

	public DataUnitSchemaDAO(final @NonNull JpaRepository<DataUnitSchemaEntity, Long> repository) {
		this.repository = repository;
	}

	@Override
	public DataUnitSchemaEntity save(final @NonNull DataUnitSchemaEntity dataUnitSchemaEntity) {
		return repository.save(dataUnitSchemaEntity);
	}

	@Override
	public Optional<DataUnitSchemaEntity> findById(final @NonNull Long id) {
		return repository.findById(id);
	}

	@Override
	public List<DataUnitSchemaEntity> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(final @NonNull Long id) {
		repository.deleteById(id);
	}

}
