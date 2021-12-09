package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.GenerateMetamodel;
import com.hurynovich.data_storage.model.AbstractServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@GenerateMetamodel
public class DataUnitSchemaServiceModelImpl extends AbstractServiceModel<Long> implements DataUnitSchemaServiceModel {

	private final String name;

	private final List<DataUnitPropertySchemaServiceModel> propertySchemas;

	public DataUnitSchemaServiceModelImpl(final Long id, final String name,
										  final List<DataUnitPropertySchemaServiceModel> propertySchemas) {
		super(id);
		this.name = name;
		this.propertySchemas = CollectionUtils.isEmpty(propertySchemas) ?
				Collections.emptyList() : Collections.unmodifiableList(propertySchemas);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<DataUnitPropertySchemaServiceModel> getPropertySchemas() {
		return propertySchemas;
	}
}
