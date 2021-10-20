package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.dao.DAO;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import com.hurynovich.data_storage.service.dto_service.DTOService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataUnitSchemaDTOService implements DTOService<DataUnitSchemaDTO, Long> {

	private final DAO<DataUnitSchemaEntity, Long> dao;

	private final DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter;

	private final Cache<Long, DataUnitSchemaDTO> cache;

	public DataUnitSchemaDTOService(final @NonNull DAO<DataUnitSchemaEntity, Long> dao,
									final @NonNull DTOConverter<DataUnitSchemaDTO, DataUnitSchemaEntity> converter,
									final @NonNull Cache<Long, DataUnitSchemaDTO> cache) {
		this.dao = dao;
		this.converter = converter;
		this.cache = cache;
	}

	@Override
	public DataUnitSchemaDTO save(final @NonNull DataUnitSchemaDTO dataUnitSchema) {
		final DataUnitSchemaDTO storedDataUnitSchema = converter.convertToDTO(
				dao.save(converter.convertFromDTO(dataUnitSchema)));

		cache.store(storedDataUnitSchema.getId(), dataUnitSchema);

		return dataUnitSchema;
	}

	@Override
	public Optional<DataUnitSchemaDTO> findById(final @NonNull Long id) {
		if (!cache.contains(id)) {
			final Optional<DataUnitSchemaEntity> dataUnitSchemaEntityOptional = dao.findById(id);
			if (dataUnitSchemaEntityOptional.isPresent()) {
				final DataUnitSchemaEntity dataUnitSchemaEntity = dataUnitSchemaEntityOptional.get();
				final DataUnitSchemaDTO dataUnitSchemaDTO = converter.convertToDTO(dataUnitSchemaEntity);

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
	public List<DataUnitSchemaDTO> findAll() {
		return converter.convertAllToDTOs(dao.findAll());
	}

	@Override
	public void deleteById(final @NonNull Long id) {
		dao.deleteById(id);

		if (cache.contains(id)) {
			cache.invalidate(id);
		}
	}

}
