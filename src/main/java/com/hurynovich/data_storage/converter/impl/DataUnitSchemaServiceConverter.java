package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class DataUnitSchemaServiceConverter
		implements ServiceConverter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> {

	private final ServiceConverter<DataUnitPropertySchemaServiceModel, DataUnitPropertySchemaPersistentModel> propertySchemaConverter;

	public DataUnitSchemaServiceConverter(
			final @NonNull ServiceConverter<DataUnitPropertySchemaServiceModel, DataUnitPropertySchemaPersistentModel> propertySchemaConverter) {
		this.propertySchemaConverter = Objects.requireNonNull(propertySchemaConverter);
	}

	@Override
	public DataUnitSchemaPersistentModel convert(final @Nullable DataUnitSchemaServiceModel source,
												 final @Nullable String... ignoreProperties) {
		final DataUnitSchemaEntity target;
		if (source != null) {
			target = new DataUnitSchemaEntity();

			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final Long id;
			if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}
			target.setId(id);

			final String name;
			if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.NAME)) {
				name = source.getName();
			} else {
				name = null;
			}
			target.setName(name);

			final List<DataUnitPropertySchemaPersistentModel> propertySchemas;
			if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.PROPERTY_SCHEMAS)) {
				propertySchemas = MassProcessingUtils.
						processQuietly(source.getPropertySchemas(), propertySchemaConverter::convert);
			} else {
				propertySchemas = List.of();
			}
			target.setPropertySchemas(propertySchemas);
		} else {
			target = null;
		}

		return target;
	}

	private Set<String> resolveIgnoreProperties(final @Nullable String... ignoreProperties) {
		return ignoreProperties != null ?
				Set.of(ignoreProperties) : Set.of();
	}

	@Override
	public DataUnitSchemaServiceModel convert(final @Nullable DataUnitSchemaPersistentModel source,
											  final @Nullable String... ignoreProperties) {
		final DataUnitSchemaServiceModelImpl target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final Long id;
			if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}

			final String name;
			if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.NAME)) {
				name = source.getName();
			} else {
				name = null;
			}

			final List<DataUnitPropertySchemaServiceModel> propertySchemas;
			if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.PROPERTY_SCHEMAS)) {
				propertySchemas = MassProcessingUtils.processQuietly(source.getPropertySchemas(),
						propertySchemaConverter::convert);
			} else {
				propertySchemas = List.of();
			}

			target = new DataUnitSchemaServiceModelImpl(id, name, propertySchemas);
		} else {
			target = null;
		}

		return target;
	}
}
