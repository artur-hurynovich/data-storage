package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.AbstractDTO;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class DataUnitDTO extends AbstractDTO<String> {

	private final Long schemaId;

	private final List<DataUnitPropertyDTO> properties;

	public DataUnitDTO(final String id, final Long schemaId, final List<DataUnitPropertyDTO> properties) {
		super(id);
		this.schemaId = schemaId;
		this.properties = CollectionUtils.isEmpty(properties) ?
				Collections.emptyList() : Collections.unmodifiableList(properties);
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public List<DataUnitPropertyDTO> getProperties() {
		return properties;
	}

	public static class DataUnitPropertyDTO {

		private final Long schemaId;

		private final Object value;

		public DataUnitPropertyDTO(final Long schemaId, final Object value) {
			this.schemaId = schemaId;
			this.value = value;
		}

		public Long getSchemaId() {
			return schemaId;
		}

		public Object getValue() {
			return value;
		}
	}
}
