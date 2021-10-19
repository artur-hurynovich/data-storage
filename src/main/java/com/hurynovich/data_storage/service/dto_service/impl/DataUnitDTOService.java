package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.document.DataUnitDocument;
import com.hurynovich.data_storage.model.dto.DataUnitDTO;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataUnitDTOService implements DTOService<DataUnitDTO, String> {

	private final DAO<DataUnitDocument, String> dao;

	private final DTOConverter<DataUnitDTO, DataUnitDocument> converter;

	public DataUnitDTOService(final @NonNull DAO<DataUnitDocument, String> dao,
							  final @NonNull DTOConverter<DataUnitDTO, DataUnitDocument> converter) {
		this.dao = dao;
		this.converter = converter;
	}

	@Override
	public DataUnitDTO save(final @NonNull DataUnitDTO dataUnit) {
		return converter.convertToDTO(dao.save(converter.convertFromDTO(dataUnit)));
	}

	@Override
	public Optional<DataUnitDTO> findById(final @NonNull String id) {
		final Optional<DataUnitDocument> optionalResult = dao.findById(id);

		return optionalResult.map(converter::convertToDTO);
	}

	@Override
	public List<DataUnitDTO> findAll() {
		return converter.convertAllToDTOs(dao.findAll());
	}

	@Override
	public void deleteById(final @NonNull String id) {
		dao.deleteById(id);
	}

}
