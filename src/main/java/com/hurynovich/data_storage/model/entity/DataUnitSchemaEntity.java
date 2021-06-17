package com.hurynovich.data_storage.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "DATA_UNIT_SCHEMA")
public class DataUnitSchemaEntity extends AbstractEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@OneToMany
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
