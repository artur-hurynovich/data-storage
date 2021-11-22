package com.hurynovich.data_storage.test_objects_asserter.model;

import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.utils.MassProcessingUtils;

import java.util.List;

public class DataUnitSchemaWrapper implements Identified<Long> {

	private final Long id;

	private final String name;

	private final List<DataUnitPropertySchemaWrapper> propertySchemas;

	private DataUnitSchemaWrapper(final Long id, final String name,
								  final List<DataUnitPropertySchemaWrapper> propertySchemas) {
		this.id = id;
		this.name = name;
		this.propertySchemas = propertySchemas;
	}

	public static DataUnitSchemaWrapper of(final DataUnitSchemaDTO schema) {
		return new DataUnitSchemaWrapper(schema.getId(), schema.getName(),
				MassProcessingUtils.processQuietly(schema.getPropertySchemas(), DataUnitPropertySchemaWrapper::of));
	}

	public static DataUnitSchemaWrapper of(final DataUnitSchemaEntity schema) {
		return new DataUnitSchemaWrapper(schema.getId(), schema.getName(),
				MassProcessingUtils.processQuietly(schema.getPropertySchemas(), DataUnitPropertySchemaWrapper::of));
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<DataUnitPropertySchemaWrapper> getPropertySchemas() {
		return propertySchemas;
	}

}
