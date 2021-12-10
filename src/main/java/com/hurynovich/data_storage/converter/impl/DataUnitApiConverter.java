package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ApiConverter;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitApiModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyApiModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyApiModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataUnitApiConverter implements ApiConverter<DataUnitApiModel, DataUnitServiceModel> {

	@Override
	public DataUnitServiceModel convert(final @Nullable DataUnitApiModel source,
										final @Nullable String... ignoreProperties) {
		final DataUnitServiceModelImpl target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final String id;
			if (!ignorePropertiesSet.contains(AbstractServiceModel_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}

			final Long schemaId;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.SCHEMA_ID)) {
				schemaId = source.getSchemaId();
			} else {
				schemaId = null;
			}

			final List<DataUnitPropertyServiceModel> properties;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.PROPERTIES)) {
				properties = MassProcessingUtils.
						processQuietly(source.getProperties(), this::convertPropertyToServiceModel);
			} else {
				properties = List.of();
			}

			target = new DataUnitServiceModelImpl(id, schemaId, properties);
		} else {
			target = null;
		}

		return target;
	}

	private Set<String> resolveIgnoreProperties(final @Nullable String... ignoreProperties) {
		return ignoreProperties != null ?
				Set.of(ignoreProperties) : Set.of();
	}

	private DataUnitPropertyServiceModel convertPropertyToServiceModel(
			final @Nullable DataUnitPropertyApiModel source) {
		final DataUnitPropertyServiceModelImpl target;
		if (source != null) {
			target = new DataUnitPropertyServiceModelImpl(source.getSchemaId(), source.getValue());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitApiModel convert(final @Nullable DataUnitServiceModel source,
									final @Nullable String... ignoreProperties) {
		final DataUnitApiModelImpl target;
		if (source != null) {
			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final String id;
			if (!ignorePropertiesSet.contains(AbstractServiceModel_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}

			final Long schemaId;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.SCHEMA_ID)) {
				schemaId = source.getSchemaId();
			} else {
				schemaId = null;
			}

			final List<DataUnitPropertyApiModel> properties;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.PROPERTIES)) {
				properties = MassProcessingUtils.
						processQuietly(source.getProperties(), this::convertPropertyToApiModel);
			} else {
				properties = List.of();
			}

			target = new DataUnitApiModelImpl(id, schemaId, properties);
		} else {
			target = null;
		}

		return target;
	}

	private DataUnitPropertyApiModel convertPropertyToApiModel(
			final @Nullable DataUnitPropertyServiceModel source) {
		final DataUnitPropertyApiModelImpl target;
		if (source != null) {
			target = new DataUnitPropertyApiModelImpl(source.getSchemaId(), source.getValue());
		} else {
			target = null;
		}

		return target;
	}
}
