package com.hurynovich.data_storage.repository;

import com.hurynovich.data_storage.model.entity.DataUnitSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface DataUnitSchemaRepository extends JpaRepository<DataUnitSchemaEntity, Long> {

	boolean existsByName(@NonNull String name);

	boolean existsByNameAndIdNot(@NonNull String name, @NonNull Long id);

}
