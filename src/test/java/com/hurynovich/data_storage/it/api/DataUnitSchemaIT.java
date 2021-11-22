package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.controller.model.GenericValidatedResponse;
import com.hurynovich.data_storage.it.api.initializer.ITInitializer;
import com.hurynovich.data_storage.model.AbstractEntity_;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertySchemaDTO;
import com.hurynovich.data_storage.model.data_unit_property_schema.DataUnitPropertyType;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity_;
import com.hurynovich.data_storage.service.paginator.model.GenericPage;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import com.hurynovich.data_storage.utils.TestReflectionUtils;
import com.hurynovich.data_storage.validator.model.ValidationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataUnitSchemaIT extends AbstractAPITest {

	private static final String UPDATED_SCHEMA_NAME = "Schema Name UPD";

	private static final String UPDATED_PROPERTY_SCHEMA_NAME = "Property Schema Name UPD";

	private final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	private final TestIdentifiedObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	private final ITInitializer<DataUnitSchemaEntity> initializer;

	@Autowired
	public DataUnitSchemaIT(final ITInitializer<DataUnitSchemaEntity> initializer) {
		this.initializer = initializer;
	}

	@AfterEach
	public void clear() {
		initializer.clear();
	}

	@Test
	void postSchemaTest() {
		final DataUnitSchemaDTO body = dtoGenerator.generateObjectNullId();
		requestAsserter.assertRequest(
				HttpMethod.POST,
				"/schema",
				body,
				HttpStatus.CREATED,
				new GenericValidatedResponse<>(new ValidationResult(), body),
				AbstractEntity_.ID);
	}

	@Test
	void getSchemaByIdTest() {
		final DataUnitSchemaEntity savedSchema = initializer.init(entityGenerator.generateObjectNullId());
		requestAsserter.assertRequest(
				HttpMethod.GET,
				"/schema/" + savedSchema.getId(),
				HttpStatus.OK,
				new GenericValidatedResponse<>(new ValidationResult(), dtoGenerator.generateObject()),
				AbstractEntity_.ID);
	}

	@Test
	void getSchemasTest() {
		entityGenerator.generateObjectsNullId().forEach(initializer::init);

		final List<DataUnitSchemaDTO> savedSchemas = dtoGenerator.generateObjects();
		savedSchemas.forEach(savedSchema ->
				TestReflectionUtils.setField(savedSchema, DataUnitSchemaEntity_.PROPERTY_SCHEMAS, new ArrayList<>()));

		final GenericPage<DataUnitSchemaDTO> page = GenericPage.
				builder(savedSchemas).
				withTotalElementsCount(3L).
				withPreviousPageNumber(null).
				withCurrentPageNumber(1L).
				withNextPageNumber(null).
				withTotalPagesCount(1L).
				build();
		final GenericValidatedResponse<GenericPage<DataUnitSchemaDTO>> expectedBody =
				new GenericValidatedResponse<>(new ValidationResult(), page);
		requestAsserter.assertRequest(
				HttpMethod.GET,
				"/schemas?pageNumber=1",
				HttpStatus.OK,
				expectedBody,
				AbstractEntity_.ID);
	}

	@Test
	void putSchemaTest() {
		final Long schemaId = initializer.init(entityGenerator.generateObjectNullId()).getId();
		final DataUnitSchemaDTO schema = new DataUnitSchemaDTO(schemaId,
				UPDATED_SCHEMA_NAME,
				Collections.singletonList(new DataUnitPropertySchemaDTO(
						null, UPDATED_PROPERTY_SCHEMA_NAME, DataUnitPropertyType.BOOLEAN)));
		final GenericValidatedResponse<DataUnitSchemaDTO> expectedBody =
				new GenericValidatedResponse<>(new ValidationResult(), schema);
		requestAsserter.assertRequest(
				HttpMethod.PUT,
				"/schema/" + schemaId,
				schema,
				HttpStatus.OK,
				expectedBody,
				AbstractEntity_.ID);
	}

	@Test
	void deleteSchemaByIdTest() {
		final DataUnitSchemaEntity schema = entityGenerator.generateObjectNullId();
		final DataUnitSchemaEntity savedSchema = initializer.init(schema);
		requestAsserter.assertRequest(
				HttpMethod.DELETE,
				"/schema/" + savedSchema.getId(),
				HttpStatus.NO_CONTENT,
				new GenericValidatedResponse<>(new ValidationResult(), null));
	}

}
