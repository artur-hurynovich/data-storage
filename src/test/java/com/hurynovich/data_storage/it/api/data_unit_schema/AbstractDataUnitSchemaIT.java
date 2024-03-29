package com.hurynovich.data_storage.it.api.data_unit_schema;

import com.hurynovich.data_storage.it.api.AbstractAPITest;
import com.hurynovich.data_storage.it.test_dao.TestDAO;
import com.hurynovich.data_storage.model.ModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaApiModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModel;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaPersistentModelGenerator;
import com.hurynovich.data_storage.model.data_unit_schema.DataUnitSchemaServiceModel;
import com.hurynovich.data_storage.model_asserter.ModelAsserter;
import com.hurynovich.data_storage.model_asserter.impl.DataUnitSchemaAsserter;
import org.springframework.beans.factory.annotation.Autowired;

class AbstractDataUnitSchemaIT extends AbstractAPITest {

	@Autowired
	protected TestDAO<DataUnitSchemaPersistentModel, Long> testDAO;

	protected final ModelGenerator<DataUnitSchemaApiModel> apiModelGenerator =
			new DataUnitSchemaApiModelGenerator();

	protected final ModelGenerator<DataUnitSchemaPersistentModel> persistentModelGenerator =
			new DataUnitSchemaPersistentModelGenerator();

	protected final ModelAsserter<DataUnitSchemaApiModel, DataUnitSchemaServiceModel, DataUnitSchemaPersistentModel> schemaAsserter =
			new DataUnitSchemaAsserter();
}
