package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaApiModelImpl;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelImpl;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModelImpl_;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataUnitSchemaApiConverter
		implements ApiConverter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel> {

	@Override
	public DataUnitSchemaServiceModel convert(final @Nullable DataUnitSchemaApiModel source,
											  final @Nullable String... ignoreProperties) {
		final DataUnitSchemaServiceModelImpl target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final Long id;
			if (!ignorePropertiesSet.contains(AbstractServiceModel_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}

			final String name;
			if (!ignorePropertiesSet.contains(DataUnitSchemaServiceModelImpl_.NAME)) {
				name = source.getName();
			} else {
				name = null;
			}

			final List<DataUnitPropertySchemaServiceModel> propertySchemas;
			if (!ignorePropertiesSet.contains(DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS)) {
				propertySchemas = MassProcessingUtils.processQuietly(source.getPropertySchemas(),
						this::convertPropertySchema);
			} else {
				propertySchemas = List.of();
			}

			target = new DataUnitSchemaServiceModelImpl(id, name, propertySchemas);
		} else {
			target = null;
		}

		return target;
	}

	private Set<String> resolveIgnoreProperties(final @Nullable String... ignoreProperties) {
		return ignoreProperties != null ?
				Set.of(ignoreProperties) : Set.of();
	}

	private DataUnitPropertySchemaServiceModel convertPropertySchema(
			final @Nullable DataUnitPropertySchemaApiModel source) {
		final DataUnitPropertySchemaServiceModelImpl target;
		if (source != null) {
			target = new DataUnitPropertySchemaServiceModelImpl(source.getId(), source.getName(), source.getType());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitSchemaApiModel convert(final @Nullable DataUnitSchemaServiceModel source,
										  final @Nullable String... ignoreProperties) {
		final DataUnitSchemaApiModelImpl target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final Long id;
			if (!ignorePropertiesSet.contains(AbstractServiceModel_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}

			final String name;
			if (!ignorePropertiesSet.contains(DataUnitSchemaServiceModelImpl_.NAME)) {
				name = source.getName();
			} else {
				name = null;
			}

			final List<DataUnitPropertySchemaApiModel> propertySchemas;
			if (!ignorePropertiesSet.contains(DataUnitSchemaServiceModelImpl_.PROPERTY_SCHEMAS)) {
				propertySchemas = MassProcessingUtils.processQuietly(source.getPropertySchemas(),
						this::convertPropertySchema);
			} else {
				propertySchemas = List.of();
			}

			target = new DataUnitSchemaApiModelImpl(id, name, propertySchemas);
		} else {
			target = null;
		}

		return target;
	}

	private DataUnitPropertySchemaApiModel convertPropertySchema(
			final @Nullable DataUnitPropertySchemaServiceModel source) {
		final DataUnitPropertySchemaApiModelImpl target;
		if (source != null) {
			target = new DataUnitPropertySchemaApiModelImpl(source.getId(), source.getName(), source.getType());
		} else {
			target = null;
		}

		return target;
	}
}
