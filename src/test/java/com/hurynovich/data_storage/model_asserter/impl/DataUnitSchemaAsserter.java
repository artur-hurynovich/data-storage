package com.hurynovich.data_storage.model_asserter.impl;

import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaServiceModel;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataUnitSchemaAsserter implements ModelAsserter<DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> {

	@Override
	public void assertEquals(final DataUnitSchemaServiceModel expected, final DataUnitSchemaServiceModel actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	private void assertEquals(final DataUnitSchemaWrapper expected, final DataUnitSchemaWrapper actual,
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

	@Override
	public void assertEquals(final DataUnitSchemaServiceModel expected, final DataUnitSchemaPersistentModel actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitSchemaPersistentModel expected, final DataUnitSchemaServiceModel actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
	}

	@Override
	public void assertEquals(final DataUnitSchemaPersistentModel expected, final DataUnitSchemaPersistentModel actual,
							 final String... ignoreProperties) {
		assertEquals(DataUnitSchemaWrapper.of(expected), DataUnitSchemaWrapper.of(actual), ignoreProperties);
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

		public static DataUnitSchemaWrapper of(final DataUnitSchemaPersistentModel schema) {
			return new DataUnitSchemaWrapper(schema.getId(), schema.getName(),
					MassProcessingUtils.processQuietly(schema.getPropertySchemas(), DataUnitPropertySchemaWrapper::of));
		}

		public static DataUnitSchemaWrapper of(final DataUnitSchemaServiceModel schema) {
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

		public static DataUnitPropertySchemaWrapper of(
				final DataUnitPropertySchemaPersistentModel dataUnitPropertySchema) {
			return new DataUnitPropertySchemaWrapper(dataUnitPropertySchema.getId(), dataUnitPropertySchema.getName(),
					dataUnitPropertySchema.getType());
		}

		public static DataUnitPropertySchemaWrapper of(
				final DataUnitPropertySchemaServiceModel dataUnitPropertySchema) {
			return new DataUnitPropertySchemaWrapper(dataUnitPropertySchema.getId(), dataUnitPropertySchema.getName(),
					dataUnitPropertySchema.getType());
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
