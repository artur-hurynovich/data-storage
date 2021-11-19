package com.hurynovich.data_storage.filter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DataUnitFilter {

	private final Long schemaId;

	private final List<DataUnitPropertyCriteria> criteria;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public DataUnitFilter(@JsonProperty final @NonNull Long schemaId,
						  @JsonProperty final @NonNull List<DataUnitPropertyCriteria> criteria) {
		this.schemaId = Objects.requireNonNull(schemaId);
		this.criteria = Collections.unmodifiableList(criteria);
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public List<DataUnitPropertyCriteria> getCriteria() {
		return criteria;
	}

}
