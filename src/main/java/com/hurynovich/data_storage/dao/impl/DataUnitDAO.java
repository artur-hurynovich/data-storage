package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.BaseDAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
class DataUnitDAO implements BaseDAO<DataUnitDocument, String> {

	private final MongoRepository<DataUnitDocument, String> repository;

	public DataUnitDAO(final @NonNull MongoRepository<DataUnitDocument, String> repository) {
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
	public void deleteById(final @NonNull String id) {
		repository.deleteById(id);
	}

}
