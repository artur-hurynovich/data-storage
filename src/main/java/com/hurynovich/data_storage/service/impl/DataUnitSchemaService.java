package com.hurynovich.data_storage.service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.Converter;
import com.hurynovich.data_storage.model.dto.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataUnitSchemaService extends GenericDTOService<DataUnitSchemaDTO, DataUnitSchemaEntity, Long> {

	private final Cache<Long, DataUnitSchemaDTO> cache;

	public DataUnitSchemaService(final PagingAndSortingRepository<DataUnitSchemaEntity, Long> repository,
								 final Converter<DataUnitSchemaDTO, DataUnitSchemaEntity> converterToPersistent,
								 final Converter<DataUnitSchemaEntity, DataUnitSchemaDTO> converterFromPersistent,
								 final Cache<Long, DataUnitSchemaDTO> cache) {
		super(repository, converterToPersistent, converterFromPersistent);

		this.cache = cache;
	}

	@Override
	public DataUnitSchemaDTO save(final DataUnitSchemaDTO dataUnitSchemaDTO) {
		final DataUnitSchemaDTO schema = super.save(dataUnitSchemaDTO);

		return cache.update(schema.getId(), schema);
	}

	@Override
	public Optional<DataUnitSchemaDTO> findById(final Long id) {
		return Optional.ofNullable(cache.get(id));
	}

	@Override
	public List<DataUnitSchemaDTO> findAll() {
		return cache.getAll();
	}

	@Override
	public DataUnitSchemaDTO update(final DataUnitSchemaDTO dataUnitSchemaDTO) {
		final DataUnitSchemaDTO schema = super.update(dataUnitSchemaDTO);

		return cache.update(schema.getId(), schema);
	}

	@Override
	public void deleteById(final Long id) {
		super.deleteById(id);

		cache.invalidate(id);
	}

}
