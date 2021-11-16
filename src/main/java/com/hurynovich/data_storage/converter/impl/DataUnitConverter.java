package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.model.AbstractDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDTO.DataUnitPropertyDTO;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument;
import com.hurynovich.data_storage.model.data_unit.DataUnitDocument.DataUnitPropertyDocument;
import com.hurynovich.data_storage.utils.MassProcessingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
class DataUnitConverter extends AbstractConverter<DataUnitDTO, DataUnitDocument, String> {

	public DataUnitConverter(final @NonNull ModelMapper modelMapper) {
		super(Objects.requireNonNull(modelMapper), Map.of(
				0, new ArgDescriptor<>("id", String.class, AbstractDocument::getId),
				1, new ArgDescriptor<>("schemaId", Long.class, DataUnitDocument::getSchemaId),
				2, new ArgDescriptor<>("properties", List.class, dataUnit -> MassProcessingUtils.
						processQuietly(dataUnit.getProperties(), convertPropertyFunction()))));
	}

	private static Function<DataUnitPropertyDocument, DataUnitPropertyDTO> convertPropertyFunction() {
		return source -> {
			final DataUnitPropertyDTO target;
			if (source != null) {
				target = new DataUnitPropertyDTO(source.getSchemaId(), source.getValue());
			} else {
				target = null;
			}

			return target;
		};
	}

	@Override
	protected Class<DataUnitDocument> getTargetClass() {
		return DataUnitDocument.class;
	}

	@Override
	protected Class<DataUnitDTO> getDTOClass() {
		return DataUnitDTO.class;
	}

}
