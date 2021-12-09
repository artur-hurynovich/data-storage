package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "DS_DATA_UNIT_SCHEMA")
public class DataUnitSchemaEntity extends AbstractEntity<Long> implements DataUnitSchemaPersistentModel {

	@Column(name = "NAME", nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = DataUnitPropertySchemaEntity.class)
	@JoinColumn(name = "SCHEMA_ID", nullable = false)
	private List<DataUnitPropertySchemaPersistentModel> propertySchemas;

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public List<DataUnitPropertySchemaPersistentModel> getPropertySchemas() {
		return propertySchemas;
	}

	public void setPropertySchemas(final List<DataUnitPropertySchemaPersistentModel> propertySchemas) {
		this.propertySchemas = propertySchemas;
	}
}
