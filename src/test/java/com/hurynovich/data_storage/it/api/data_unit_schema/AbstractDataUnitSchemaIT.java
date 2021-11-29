package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.it.api.AbstractAPITest;
import com.hurynovich.data_storage.it.test_dao.TestDAO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaDTO;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaEntity;
import com.hurynovich.data_storage.test_object_generator.TestIdentifiedObjectGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaDTOGenerator;
import com.hurynovich.data_storage.test_object_generator.impl.TestDataUnitSchemaEntityGenerator;
import com.hurynovich.data_storage.test_objects_asserter.TestIdentifiedObjectsAsserter;
import com.hurynovich.data_storage.test_objects_asserter.impl.DataUnitSchemaAsserter;
import org.springframework.beans.factory.annotation.Autowired;

class AbstractDataUnitSchemaIT extends AbstractAPITest {

	@Autowired
	protected TestDAO<DataUnitSchemaEntity, Long> testDAO;

	protected final TestIdentifiedObjectGenerator<DataUnitSchemaDTO> dtoGenerator =
			new TestDataUnitSchemaDTOGenerator();

	protected final TestIdentifiedObjectGenerator<DataUnitSchemaEntity> entityGenerator =
			new TestDataUnitSchemaEntityGenerator();

	protected final TestIdentifiedObjectsAsserter<DataUnitSchemaDTO, DataUnitSchemaEntity> schemaAsserter =
			new DataUnitSchemaAsserter();
}
