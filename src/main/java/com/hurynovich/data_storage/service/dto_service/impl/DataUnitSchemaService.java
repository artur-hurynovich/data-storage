package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
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

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	public DataUnitSchemaService(final JpaRepository<DataUnitSchemaEntity, Long> repository,
								 final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter) {
		this.repository = repository;
		this.converter = converter;
	}

	@Override
	public DataUnitSchemaDTO save(final DataUnitSchemaDTO dataUnitSchema) {
		return converter.convertToDTO(repository.save(converter.convertFromDTO(dataUnitSchema)));
	}

	@Override
	public Optional<DataUnitSchemaDTO> findById(final Long id) {
		final Optional<DataUnitSchemaEntity> optionalResult = repository.findById(id);

		return optionalResult.map(converter::convertToDTO);
	}

	@Override
	public List<DataUnitSchemaDTO> findAll() {
		return converter.convertAllToDTOs(repository.findAll());
	}

	@Override
	public void deleteById(final Long id) {
		repository.deleteById(id);
	}

}
