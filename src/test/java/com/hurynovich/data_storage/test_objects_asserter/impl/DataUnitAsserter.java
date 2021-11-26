package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.model.AbstractDocument_;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument_;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataUnitAsserter implements TestIdentifiedObjectsAsserter<DataUnitDTO, DataUnitDocument> {

	@Override
	public void assertEquals(final DataUnitDTO expected, final DataUnitDTO actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitWrapper.of(expected), DataUnitWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitDTO expected, final DataUnitDocument actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitWrapper.of(expected), DataUnitWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitDocument expected, final DataUnitDocument actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitWrapper.of(expected), DataUnitWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitDocument expected, final DataUnitDTO actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitWrapper.of(expected), DataUnitWrapper.of(actual), ignoreProperties);
	}

	private void assertEquals(final DataUnitWrapper expected, final DataUnitWrapper actual,
							  final String... ignoreProperties) {
		final Set<String> ignorePropertiesSet = (ignoreProperties != null ?
				Set.of(ignoreProperties) : Collections.emptySet());
		if (!ignorePropertiesSet.contains(AbstractDocument_.ID)) {
			Assertions.assertEquals(expected.getId(), actual.getId());
		}

		if (!ignorePropertiesSet.contains(DataUnitDocument_.SCHEMA_ID)) {
			Assertions.assertEquals(expected.getSchemaId(), actual.getSchemaId());
		}

		if (!ignorePropertiesSet.contains(DataUnitDocument_.PROPERTIES)) {
			final List<DataUnitPropertyWrapper> expectedProperties = expected.getProperties();
			final List<DataUnitPropertyWrapper> actualProperties = actual.getProperties();
			Assertions.assertEquals(expectedProperties.size(), actualProperties.size());
			for (int i = 0; i < expectedProperties.size(); i++) {
				final DataUnitPropertyWrapper expectedProperty = expectedProperties.get(i);
				final DataUnitPropertyWrapper actualProperty = actualProperties.get(i);
				Assertions.assertEquals(expectedProperty.getSchemaId(), actualProperty.getSchemaId());
				Assertions.assertEquals(expectedProperty.getValue(), actualProperty.getValue());
			}
		}
	}

	private static class DataUnitWrapper {

		private final String id;

		private final Long schemaId;

		private final List<DataUnitPropertyWrapper> properties;

		private DataUnitWrapper(final String id, final Long schemaId, final List<DataUnitPropertyWrapper> properties) {
			this.id = id;
			this.schemaId = schemaId;
			this.properties = properties;
		}

		public static DataUnitWrapper of(final DataUnitDTO dataUnit) {
			return new DataUnitWrapper(dataUnit.getId(), dataUnit.getSchemaId(),
					MassProcessingUtils.processQuietly(dataUnit.getProperties(), DataUnitPropertyWrapper::of));
		}

		public static DataUnitWrapper of(final DataUnitDocument dataUnit) {
			return new DataUnitWrapper(dataUnit.getId(), dataUnit.getSchemaId(),
					MassProcessingUtils.processQuietly(dataUnit.getProperties(), DataUnitPropertyWrapper::of));
		}

		public String getId() {
			return id;
		}

		public Long getSchemaId() {
			return schemaId;
		}

		public List<DataUnitPropertyWrapper> getProperties() {
			return properties;
		}

	}

	private static class DataUnitPropertyWrapper {

		private final Long schemaId;

		private final Object value;

		public DataUnitPropertyWrapper(final Long schemaId, final Object value) {
			this.schemaId = schemaId;
			this.value = value;
		}

		public static DataUnitPropertyWrapper of(final DataUnitDTO.DataUnitPropertyDTO dataUnitProperty) {
			return new DataUnitPropertyWrapper(dataUnitProperty.getSchemaId(), dataUnitProperty.getValue());
		}

		public static DataUnitPropertyWrapper of(final DataUnitDocument.DataUnitPropertyDocument dataUnitProperty) {
			return new DataUnitPropertyWrapper(dataUnitProperty.getSchemaId(), dataUnitProperty.getValue());
		}

		public Long getSchemaId() {
			return schemaId;
		}

		public Object getValue() {
			return value;
		}

	}

}
