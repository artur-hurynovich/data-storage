package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.AbstractApiModel;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class DataUnitApiModelImpl extends AbstractApiModel<String> implements DataUnitApiModel {

	private final Long schemaId;

	private final List<DataUnitPropertyApiModel> properties;

	public DataUnitApiModelImpl(final String id, final Long schemaId,
								final List<DataUnitPropertyApiModel> properties) {
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
	public List<DataUnitPropertyApiModel> getProperties() {
		return properties;
	}
}
