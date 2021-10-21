package com.hurynovich.data_storage.model.data_unit_schema;

import com.hurynovich.data_storage.model.AbstractEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "DS_DATA_UNIT_SCHEMA")
public class DataUnitSchemaEntity extends AbstractEntity<Long> {

	@Column(name = "NAME", nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SCHEMA_ID", nullable = false)
	private List<DataUnitPropertySchemaEntity> propertySchemas;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<DataUnitPropertySchemaEntity> getPropertySchemas() {
		return propertySchemas;
	}

	public void setPropertySchemas(final List<DataUnitPropertySchemaEntity> propertySchemas) {
		this.propertySchemas = propertySchemas;
	}

}
