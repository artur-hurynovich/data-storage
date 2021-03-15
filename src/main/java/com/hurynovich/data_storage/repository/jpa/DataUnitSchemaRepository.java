package com.hurynovich.data_storage.repository.jpa;

import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataUnitSchemaRepository extends JpaRepository<DataUnitSchemaEntity, Long> {

}
