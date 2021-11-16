package com.hurynovich.data_storage.dao;

import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import org.springframework.lang.NonNull;

public interface DataUnitDAO extends BaseDAO<DataUnitDocument, String> {

	void deleteAllBySchemaId(@NonNull Long schemaId);

}
