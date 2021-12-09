package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.ServiceConverter;
import com.hurynovich.data_storage.model.AbstractServiceModel_;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyPersistentModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitPropertyServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModel;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl.DataUnitPropertyServiceModelImpl;
import com.hurynovich.data_storage.model.data_unit.DataUnitServiceModelImpl_;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataUnitServiceConverter implements ServiceConverter<DataUnitServiceModel, DataUnitPersistentModel> {

	@Override
	public DataUnitPersistentModel convert(final @Nullable DataUnitServiceModel source,
										   final @Nullable String... ignoreProperties) {
		final DataUnitDocument target;
		if (source != null) {
			target = new DataUnitDocument();

			final Set<String> ignorePropertiesSet = resolveIgnoreProperties(ignoreProperties);
			final String id;
			if (!ignorePropertiesSet.contains(AbstractServiceModel_.ID)) {
				id = source.getId();
			} else {
				id = null;
			}
			target.setId(id);

			final Long schemaId;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.SCHEMA_ID)) {
				schemaId = source.getSchemaId();
			} else {
				schemaId = null;
			}
			target.setSchemaId(schemaId);

			final List<DataUnitPropertyPersistentModel> properties;
			if (!ignorePropertiesSet.contains(DataUnitServiceModelImpl_.PROPERTIES)) {
				properties = MassProcessingUtils.
						processQuietly(source.getProperties(), this::convertPropertyToPersistentModel);
			} else {
				properties = List.of();
			}
			target.setProperties(properties);
		} else {
			target = null;
		}

		return target;
	}

	private Set<String> resolveIgnoreProperties(final @Nullable String... ignoreProperties) {
		return ignoreProperties != null ?
				Set.of(ignoreProperties) : Set.of();
	}

	private DataUnitPropertyPersistentModel convertPropertyToPersistentModel(
			final @Nullable DataUnitPropertyServiceModel source) {
		final DataUnitPropertyDocument target;
		if (source != null) {
			target = new DataUnitPropertyDocument();
			target.setSchemaId(source.getSchemaId());
			target.setValue(source.getValue());
		} else {
			target = null;
		}

		return target;
	}

	@Override
	public DataUnitServiceModel convert(final @Nullable DataUnitPersistentModel source,
										final @Nullable String... ignoreProperties) {
		final DataUnitServiceModel target;
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
						processQuietly(source.getProperties(), this::convertPropertyToDTOModel);
			} else {
				properties = List.of();
			}

			target = new DataUnitServiceModelImpl(id, schemaId, properties);
		} else {
			target = null;
		}

		return target;
	}

	private DataUnitPropertyServiceModel convertPropertyToDTOModel(
			final @Nullable DataUnitPropertyPersistentModel source) {
		final DataUnitPropertyServiceModelImpl target;
		if (source != null) {
			target = new DataUnitPropertyServiceModelImpl(source.getSchemaId(), source.getValue());
		} else {
			target = null;
		}

		return target;
	}
}
