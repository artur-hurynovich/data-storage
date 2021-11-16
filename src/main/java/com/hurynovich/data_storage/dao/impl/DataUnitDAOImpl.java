package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DataUnitDAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.repository.DataUnitRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
class DataUnitDAOImpl implements DataUnitDAO {

	private final DataUnitRepository repository;

	public DataUnitDAOImpl(final @NonNull DataUnitRepository repository) {
		this.repository = Objects.requireNonNull(repository);
	}

	@Override
	public DataUnitDocument save(final @NonNull DataUnitDocument dataUnit) {
		return repository.save(dataUnit);
	}

	@Override
	public Optional<DataUnitDocument> findById(final @NonNull String id) {
		return repository.findById(id);
	}

	@Override
	public void delete(final @NonNull DataUnitDocument dataUnit) {
		repository.delete(dataUnit);
	}

	@Override
	public void deleteAllBySchemaId(final @NonNull Long schemaId) {
		repository.deleteBySchemaId(schemaId);
	}

}
