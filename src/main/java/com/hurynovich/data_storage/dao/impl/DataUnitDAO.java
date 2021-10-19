package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataUnitDAO implements DAO<DataUnitDocument, String> {

	private final MongoRepository<DataUnitDocument, String> repository;

	public DataUnitDAO(final @NonNull MongoRepository<DataUnitDocument, String> repository) {
		this.repository = repository;
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
	public List<DataUnitDocument> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(final @NonNull String id) {
		repository.deleteById(id);
	}

}
