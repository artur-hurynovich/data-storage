package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataUnitDTOService implements DTOService<DataUnitDTO, String> {

	private final DAO<DataUnitDocument, String> dao;

	private final Converter<DataUnitDTO, DataUnitDocument> dtoConverter;

	private final Converter<DataUnitDocument, DataUnitDTO> documentConverter;

	public DataUnitDTOService(final @NonNull DAO<DataUnitDocument, String> dao,
							  final @NonNull Converter<DataUnitDTO, DataUnitDocument> dtoConverter,
							  final @NonNull Converter<DataUnitDocument, DataUnitDTO> documentConverter) {
		this.dao = dao;
		this.dtoConverter = dtoConverter;
		this.documentConverter = documentConverter;
	}

	@Override
	public DataUnitDTO save(final @NonNull DataUnitDTO dataUnit) {
		return documentConverter.convert(dao.save(dtoConverter.convert(dataUnit)));
	}

	@Override
	public Optional<DataUnitDTO> findById(final @NonNull String id) {
		final Optional<DataUnitDocument> optionalResult = dao.findById(id);

		return optionalResult.map(documentConverter::convert);
	}

	@Override
	public List<DataUnitDTO> findAll() {
		return MassProcessingUtils.processQuietly(dao.findAll(), documentConverter::convert);
	}

	@Override
	public void deleteById(final @NonNull String id) {
		dao.deleteById(id);
	}

}


