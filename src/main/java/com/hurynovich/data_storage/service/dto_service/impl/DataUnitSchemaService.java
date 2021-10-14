package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataUnitSchemaService implements DTOService<DataUnitSchemaDTO, Long> {

	private final JpaRepository<DataUnitSchemaEntity, Long> repository;

	private final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity> converterToPersistent;

	private final Converter<DataUnitSchemaEntity, DataUnitSchemaDTO> converterFromPersistent;

	public DataUnitSchemaService(final JpaRepository<DataUnitSchemaEntity, Long> repository,
								 final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity> converterToPersistent,
								 final Converter<DataUnitSchemaEntity, DataUnitSchemaDTO> converterFromPersistent) {
		this.repository = repository;
		this.converterToPersistent = converterToPersistent;
		this.converterFromPersistent = converterFromPersistent;
	}

	@Override
	public DataUnitSchemaDTO save(final DataUnitSchemaDTO dataUnitSchema) {
		return converterFromPersistent.convert(repository.save(converterToPersistent.convert(dataUnitSchema)));
	}

	@Override
	public Optional<DataUnitSchemaDTO> findById(final Long id) {
		final Optional<DataUnitSchemaEntity> optionalResult = repository.findById(id);

		return optionalResult.map(converterFromPersistent::convert);
	}

	@Override
	public List<DataUnitSchemaDTO> findAll() {
		return converterFromPersistent.convertAll(repository.findAll());
	}

	@Override
	public void deleteById(final Long id) {
		repository.deleteById(id);
	}

}
