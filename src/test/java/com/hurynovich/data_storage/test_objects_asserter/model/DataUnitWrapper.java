package com.hurynovich.data_storage.test_objects_asserter.model;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.utils.MassProcessingUtils;

import java.util.List;

public class DataUnitWrapper implements Identified<String> {

	private final String id;

	private final Long schemaId;

	private final List<DataUnitPropertyWrapper> properties;

	private DataUnitWrapper(final String id, final Long schemaId, final List<DataUnitPropertyWrapper> properties) {
		this.id = id;
		this.schemaId = schemaId;
		this.properties = properties;
	}

	public static DataUnitWrapper of(final DataUnitDTO dataUnit) {
		return new DataUnitWrapper(dataUnit.getId(), dataUnit.getSchemaId(),
				MassProcessingUtils.processQuietly(dataUnit.getProperties(), DataUnitPropertyWrapper::of));
	}

	public static DataUnitWrapper of(final DataUnitDocument dataUnit) {
		return new DataUnitWrapper(dataUnit.getId(), dataUnit.getSchemaId(),
				MassProcessingUtils.processQuietly(dataUnit.getProperties(), DataUnitPropertyWrapper::of));
	}

	@Override
	public String getId() {
		return id;
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public List<DataUnitPropertyWrapper> getProperties() {
		return properties;
	}

}
