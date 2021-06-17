package com.hurynovich.data_storage.model.entity;

import com.hurynovich.data_storage.model.DataPropertyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "DATA_UNIT_PROPERTY_SCHEMA")
public class DataUnitPropertySchemaEntity extends AbstractEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private DataPropertyType type;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public DataPropertyType getType() {
		return type;
	}

	public void setType(final DataPropertyType type) {
		this.type = type;
	}

}
