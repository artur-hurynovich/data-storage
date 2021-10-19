package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataUnitSchemaDTOService implements DTOService<DataUnitSchemaDTO, Long> {

	private final DAO<DataUnitSchemaEntity, Long> dao;

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	public DataUnitSchemaDTOService(final DAO<DataUnitSchemaEntity, Long> dao,
									final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter) {
		this.dao = dao;
		this.converter = converter;
	}

	@Override
	public DataUnitSchemaDTO save(final DataUnitSchemaDTO dataUnitSchema) {
		return converter.convertToDTO(dao.save(converter.convertFromDTO(dataUnitSchema)));
	}

	@Override
	public Optional<DataUnitSchemaDTO> findById(final Long id) {
		final Optional<DataUnitSchemaEntity> optionalResult = dao.findById(id);

		return optionalResult.map(converter::convertToDTO);
	}

	@Override
	public List<DataUnitSchemaDTO> findAll() {
		return converter.convertAllToDTOs(dao.findAll());
	}

	@Override
	public void deleteById(final Long id) {
		dao.deleteById(id);
	}

}
