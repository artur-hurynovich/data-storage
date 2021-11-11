package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaDTOService;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataUnitSchemaDTOServiceImpl implements DataUnitSchemaDTOService {

	private final DataUnitSchemaDAO dao;

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter;

	private final Cache<Long, DataUnitSchemaDTO> cache;

	public DataUnitSchemaDTOServiceImpl(final @NonNull DataUnitSchemaDAO dao,
										final @NonNull DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> converter,
										final @NonNull Cache<Long, DataUnitSchemaDTO> cache) {
		this.dao = dao;
		this.converter = converter;
		this.cache = cache;
	}

	@Override
	public DataUnitSchemaDTO save(final @NonNull DataUnitSchemaDTO dataUnitSchema) {
		final DataUnitSchemaDTO savedDataUnitSchema = converter.convert(
				dao.save(converter.convert(dataUnitSchema)));

		cache.store(savedDataUnitSchema.getId(), savedDataUnitSchema);

		return savedDataUnitSchema;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DataUnitSchemaDTO> findById(final @NonNull Long id) {
		if (!cache.contains(id)) {
			final Optional<DataUnitSchemaEntity> dataUnitSchemaEntityOptional = dao.findById(id);
			if (dataUnitSchemaEntityOptional.isPresent()) {
				final DataUnitSchemaEntity dataUnitSchemaEntity = dataUnitSchemaEntityOptional.get();
				final DataUnitSchemaDTO dataUnitSchemaDTO = converter.convert(dataUnitSchemaEntity);

				cache.store(id, dataUnitSchemaDTO);
			}
		}

		return cache.get(id);
	}

	/*
	 * We don't use cache in findAll method due to incompatibility of the cache with pagination.
	 * The main reason of using cache - are frequent calls of findById method while validating DataUnitDTO,
	 * as this method is called every time we save or update DataUnitDTO.
	 */
	@Override
	public void deleteById(final @NonNull Long id) {
		dao.deleteById(id);

		if (cache.contains(id)) {
			cache.invalidate(id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataUnitSchemaDTO> findAll(final @NonNull PaginationParams params) {
		return MassProcessingUtils.processQuietly(dao.findAll(params), dataUnitSchema -> converter.
				convert(dataUnitSchema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS));
	}

	@Override
	public long count() {
		return dao.count();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByName(final @NonNull String name) {
		return dao.existsByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByNameAndNotId(final @NonNull String name, final @NonNull Long id) {
		return dao.existsByNameAndNotId(name, id);
	}

}
