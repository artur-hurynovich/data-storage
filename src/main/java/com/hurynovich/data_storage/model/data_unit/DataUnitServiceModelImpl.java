package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.GenerateMetamodel;
import com.hurynovich.data_storage.model.AbstractServiceModel;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@GenerateMetamodel
public class DataUnitServiceModelImpl extends AbstractServiceModel<String> implements DataUnitServiceModel {

	private final Long schemaId;

	private final List<DataUnitPropertyServiceModel> properties;

	public DataUnitServiceModelImpl(final String id, final Long schemaId, final List<DataUnitPropertyServiceModel> properties) {
		super(id);
		this.schemaId = schemaId;
		this.properties = CollectionUtils.isEmpty(properties) ?
				Collections.emptyList() : Collections.unmodifiableList(properties);
	}

	@Override
	public Long getSchemaId() {
		return schemaId;
	}

	@Override
	public List<DataUnitPropertyServiceModel> getProperties() {
		return properties;
	}

	@GenerateMetamodel
	public static class DataUnitPropertyServiceModelImpl implements DataUnitPropertyServiceModel {

		private final Long schemaId;

		private final Object value;

		public DataUnitPropertyServiceModelImpl(final Long schemaId, final Object value) {
			this.schemaId = schemaId;
			this.value = value;
		}

		@Override
		public Long getSchemaId() {
			return schemaId;
		}

		@Override
		public Object getValue() {
			return value;
		}
	}
}
