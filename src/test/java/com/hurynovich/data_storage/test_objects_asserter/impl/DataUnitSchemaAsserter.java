package com.hurynovich.data_storage.test_objects_asserter.impl;

import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaEntity;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataUnitSchemaAsserter implements TestIdentifiedObjectsAsserter<DataUnitSchemaDTO, DataUnitSchemaEntity> {

	@Override
	public void assertEquals(final DataUnitSchemaDTO expected, final DataUnitSchemaDTO actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitSchemaDTO expected, final DataUnitSchemaEntity actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitSchemaEntity expected, final DataUnitSchemaEntity actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitSchemaEntity expected, final DataUnitSchemaDTO actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	public void assertEquals(final DataUnitSchemaWrapper expected, final DataUnitSchemaWrapper actual,
							 final String... ignoreProperties) {
		final Set<String> ignorePropertiesSet = (ignoreProperties != null ?
				Set.of(ignoreProperties) : Collections.emptySet());
		if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
			Assertions.assertEquals(expected.getId(), actual.getId());
		}

		if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.NAME)) {
			Assertions.assertEquals(expected.getName(), actual.getName());
		}

		if (!ignorePropertiesSet.contains(DataUnitSchemaEntity_.PROPERTY_SCHEMAS)) {
			final List<DataUnitPropertySchemaWrapper> expectedPropertySchemas = expected.getPropertySchemas();
			final List<DataUnitPropertySchemaWrapper> actualPropertySchemas = actual.getPropertySchemas();
			Assertions.assertEquals(expectedPropertySchemas.size(), actualPropertySchemas.size());
			for (int i = 0; i < expectedPropertySchemas.size(); i++) {
				final DataUnitPropertySchemaWrapper expectedPropertySchema = expectedPropertySchemas.get(i);
				final DataUnitPropertySchemaWrapper actualPropertySchema = actualPropertySchemas.get(i);

				if (!ignorePropertiesSet.contains(AbstractEntity_.ID)) {
					Assertions.assertEquals(expectedPropertySchema.getId(), actualPropertySchema.getId());
				}

				Assertions.assertEquals(expectedPropertySchema.getName(), actualPropertySchema.getName());
				Assertions.assertEquals(expectedPropertySchema.getType(), actualPropertySchema.getType());
			}
		}
	}

	private static class DataUnitSchemaWrapper {

		private final Long id;

		private final String name;

		private final List<DataUnitPropertySchemaWrapper> propertySchemas;

		private DataUnitSchemaWrapper(final Long id, final String name,
									  final List<DataUnitPropertySchemaWrapper> propertySchemas) {
			this.id = id;
			this.name = name;
			this.propertySchemas = propertySchemas;
		}

		public static DataUnitSchemaWrapper of(final DataUnitSchemaDTO schema) {
			return new DataUnitSchemaWrapper(schema.getId(), schema.getName(),
					MassProcessingUtils.processQuietly(schema.getPropertySchemas(), DataUnitPropertySchemaWrapper::of));
		}

		public static DataUnitSchemaWrapper of(final DataUnitSchemaEntity schema) {
			return new DataUnitSchemaWrapper(schema.getId(), schema.getName(),
					MassProcessingUtils.processQuietly(schema.getPropertySchemas(), DataUnitPropertySchemaWrapper::of));
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public List<DataUnitPropertySchemaWrapper> getPropertySchemas() {
			return propertySchemas;
		}

	}

	private static class DataUnitPropertySchemaWrapper {

		private final Long id;

		private final String name;

		private final DataUnitPropertyType type;

		private DataUnitPropertySchemaWrapper(final Long id, final String name, final DataUnitPropertyType type) {
			this.id = id;
			this.name = name;
			this.type = type;
		}

		public static DataUnitPropertySchemaWrapper of(final DataUnitPropertySchemaEntity schema) {
			return new DataUnitPropertySchemaWrapper(schema.getId(), schema.getName(), schema.getType());
		}

		public static DataUnitPropertySchemaWrapper of(final DataUnitPropertySchemaDTO schema) {
			return new DataUnitPropertySchemaWrapper(schema.getId(), schema.getName(), schema.getType());
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public DataUnitPropertyType getType() {
			return type;
		}

	}

}
