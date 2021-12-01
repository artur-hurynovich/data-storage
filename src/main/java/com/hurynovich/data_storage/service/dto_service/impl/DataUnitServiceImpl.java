package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.dao.DataUnitDAO;
import com.hurynovich.data_storage.filter.model.DataUnitFilter;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.service.dto_service.DataUnitService;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import com.hurynovich.data_storage.utils.ValidationErrorMessageUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
class DataUnitServiceImpl implements DataUnitService {

	private final DataUnitDAO dao;

	private final Converter<DataUnitDTO, DataUnitDocument, String> converter;

	public DataUnitServiceImpl(final @NonNull DataUnitDAO dao,
							   final @NonNull Converter<DataUnitDTO, DataUnitDocument, String> converter) {
		this.dao = Objects.requireNonNull(dao);
		this.converter = Objects.requireNonNull(converter);
	}

	@Override
	public DataUnitDTO save(final @NonNull DataUnitDTO dataUnit) {
		return converter.convert(dao.save(converter.convert(dataUnit)));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DataUnitDTO> findById(final @NonNull String id) {
		final Optional<DataUnitDocument> optionalResult = dao.findById(id);

		return optionalResult.map(converter::convert);
	}

	@Override
	public void deleteById(final @NonNull String id) {
		final Optional<DataUnitDocument> dataUnitOptional = dao.findById(id);
		if (dataUnitOptional.isPresent()) {
			dao.delete(dataUnitOptional.get());
		} else {
			throw new EntityNotFoundException(ValidationErrorMessageUtils.
					buildNotFoundByIdErrorMessage("dataUnit", id));
		}
	}

	@Override
	public List<DataUnitDTO> findAll(final @NonNull PaginationParams params, final @NonNull DataUnitFilter filter) {
		return MassProcessingUtils.processQuietly(dao.findAll(params, filter), converter::convert);
	}

	@Override
	public long count(final @NonNull DataUnitFilter filter) {
		return dao.count(filter);
	}

	@Override
	public void deleteAllBySchemaId(final @NonNull Long schemaId) {
		dao.deleteAllBySchemaId(schemaId);
	}
}


