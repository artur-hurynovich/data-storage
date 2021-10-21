package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.repository.DataUnitSchemaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataUnitSchemaDAOImpl implements DataUnitSchemaDAO {

	private final DataUnitSchemaRepository repository;

	public DataUnitSchemaDAOImpl(final @NonNull DataUnitSchemaRepository repository) {
		this.repository = repository;
	}

	@Override
	public DataUnitSchemaEntity save(final @NonNull DataUnitSchemaEntity dataUnitSchema) {
		return repository.save(dataUnitSchema);
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


	@Override
	public boolean existsByName(final @NonNull String name) {
		return repository.existsByName(name);
	}

	@Override
	public boolean existsByNameAndNotId(final @NonNull String name, final @NonNull Long id) {
		return repository.existsByNameAndIdNot(name, id);
	}

}
