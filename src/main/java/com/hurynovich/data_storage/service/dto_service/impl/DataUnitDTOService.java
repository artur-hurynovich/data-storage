package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.BaseDAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.BaseDTOService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DataUnitDTOService implements BaseDTOService<DataUnitDTO, String> {

	private final BaseDAO<DataUnitDocument, String> dao;

	private final DTOConverter<DataUnitDTO, DataUnitDocument, String> converter;

	public DataUnitDTOService(final @NonNull BaseDAO<DataUnitDocument, String> dao,
							  final @NonNull DTOConverter<DataUnitDTO, DataUnitDocument, String> converter) {
		this.dao = dao;
		this.converter = converter;
	}

	@Override
	public DataUnitDTO save(final @NonNull DataUnitDTO dataUnit) {
		return converter.convertFull(dao.save(converter.convert(dataUnit)));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DataUnitDTO> findById(final @NonNull String id) {
		final Optional<DataUnitDocument> optionalResult = dao.findById(id);

		return optionalResult.map(converter::convertFull);
	}

	@Override
	public void deleteById(final @NonNull String id) {
		dao.deleteById(id);
	}

}


