package com.hurynovich.data_storage.filter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

public class Filter {

	private final List<FilterCriteria> criteria;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public Filter(@JsonProperty final @NonNull List<FilterCriteria> criteria) {
		this.criteria = Collections.unmodifiableList(criteria);
	}

	public List<FilterCriteria> getCriteria() {
		return criteria;
	}

}
