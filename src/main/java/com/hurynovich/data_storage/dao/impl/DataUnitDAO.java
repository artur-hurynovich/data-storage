package com.hurynovich.data_storage.dao.impl;

import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataUnitDAO implements DAO<DataUnitDocument, String> {

	private final MongoRepository<DataUnitDocument, String> repository;

	public DataUnitDAO(final MongoRepository<DataUnitDocument, String> repository) {
		this.repository = repository;
	}

	@Override
	public DataUnitDocument save(final DataUnitDocument dataUnit) {
		return repository.save(dataUnit);
	}

	@Override
	public Optional<DataUnitDocument> findById(final String id) {
		return repository.findById(id);
	}

	@Override
	public List<DataUnitDocument> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(final String id) {
		repository.deleteById(id);
	}

}
