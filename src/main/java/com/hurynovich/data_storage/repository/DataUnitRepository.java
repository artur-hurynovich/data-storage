package com.hurynovich.data_storage.repository;

import com.hurynovich.data_storage.model.document.DataUnitDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataUnitRepository extends MongoRepository<DataUnitDocument, String> {

}
