package com.hurynovich.data_storage.service.dto_service.impl;

import com.hurynovich.data_storage.cache.Cache;
import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.dao.DataUnitSchemaDAO;
import com.hurynovich.data_storage.event.EventListener;
import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.event.model.EventType;
import com.hurynovich.data_storage.model.PaginationParams;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.service.dto_service.DataUnitSchemaService;
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
class DataUnitSchemaServiceImpl implements DataUnitSchemaService {

	private final DataUnitSchemaDAO dao;

	private final ServiceConverter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> converter;

	private final Cache<Long, DataUnitSchemaServiceModel> cache;

	private final EventListener<DataUnitSchemaServiceModel> eventListener;

	public DataUnitSchemaServiceImpl(final @NonNull DataUnitSchemaDAO dao,
									 final @NonNull ServiceConverter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> converter,
									 final @NonNull Cache<Long, DataUnitSchemaServiceModel> cache,
									 final @NonNull EventListener<DataUnitSchemaServiceModel> eventListener) {
		this.dao = Objects.requireNonNull(dao);
		this.converter = Objects.requireNonNull(converter);
		this.cache = Objects.requireNonNull(cache);
		this.eventListener = Objects.requireNonNull(eventListener);
	}

	@Override
	public DataUnitSchemaServiceModel save(final @NonNull DataUnitSchemaServiceModel dataUnitSchema) {
		final DataUnitSchemaServiceModel savedDataUnitSchema = converter.convert(
				dao.save(converter.convert(dataUnitSchema)));

		cache.store(savedDataUnitSchema.getId(), savedDataUnitSchema);

		return savedDataUnitSchema;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DataUnitSchemaServiceModel> findById(final @NonNull Long id) {
		if (!cache.contains(id)) {
			dao.findById(id).ifPresent(dataUnitSchemaPersistentModel -> cache.
					store(id, converter.convert(dataUnitSchemaPersistentModel)));
		}

		return cache.get(id);
	}

	@Override
	public void deleteById(final @NonNull Long id) {
		final Optional<DataUnitSchemaPersistentModel> schemaOptional = dao.findById(id);
		if (schemaOptional.isPresent()) {
			final DataUnitSchemaPersistentModel schemaPersistentModel = schemaOptional.get();
			final DataUnitSchemaServiceModel schemaServiceModel = converter.convert(schemaPersistentModel);
			dao.delete(schemaPersistentModel);

			if (cache.contains(id)) {
				cache.invalidate(id);
			}

			eventListener.onEvent(new Event<>(schemaServiceModel, EventType.DELETE));
		} else {
			throw new EntityNotFoundException(ValidationErrorMessageUtils.
					buildNotFoundByIdErrorMessage("dataUnitSchema", id));
		}
	}

	/*
	 * We don't use cache in findAll method due to incompatibility of the cache with pagination.
	 * The main reason of using cache - are frequent calls of findById method while validating DataUnitDTO,
	 * as this method is called every time we save or update DataUnitDTO.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DataUnitSchemaServiceModel> findAll(final @NonNull PaginationParams params) {
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
