package com.hurynovich.data_storage.model.data_unit;

import com.hurynovich.data_storage.model.AbstractDTO;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

@Immutable
public class DataUnitDTO extends AbstractDTO<String> {

	private final Long schemaId;

	private final List<DataUnitPropertyDTO> properties;

	public DataUnitDTO(final String id, final Long schemaId, final List<DataUnitPropertyDTO> properties) {
		super(id);
		this.schemaId = schemaId;
		this.properties = Collections.unmodifiableList(properties);
	}

	public Long getSchemaId() {
		return schemaId;
	}

	public List<DataUnitPropertyDTO> getProperties() {
		return properties;
	}

	@Immutable
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
