package com.hurynovich.data_storage.model.data_unit_property_schema;

import com.hurynovich.data_storage.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "DS_DATA_UNIT_PROPERTY_SCHEMA")
public class DataUnitPropertySchemaEntity extends AbstractEntity<Long>
		implements DataUnitPropertySchemaPersistentModel {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private DataUnitPropertyType type;

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public DataUnitPropertyType getType() {
		return type;
	}

	public void setType(final DataUnitPropertyType type) {
		this.type = type;
	}
}
