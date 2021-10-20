package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.converter.DTOConverter;
import com.hurynovich.data_storage.model.Identified;
import com.hurynovich.data_storage.model.dto.AbstractDTO;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericDTOConverter<T extends AbstractDTO<? extends Serializable>, U extends Identified<? extends Serializable>>
		implements DTOConverter<T, U> {

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public U convert(final @Nullable T source) {
		final U target;
		if (source != null) {
			target = modelMapper.map(source, getTargetClass());
		} else {
			target = null;
		}

		return target;
	}

	protected abstract Class<U> getTargetClass();

	@Override
	public T convert(final @Nullable U source) {
		final T target;
		if (source != null) {
			target = modelMapper.map(source, getDTOClass());
		} else {
			target = null;
		}

		return target;
	}

	protected abstract Class<T> getDTOClass();

	@Override
	public List<T> convert(final @Nullable Iterable<U> sources) {
		final List<T> targets = new ArrayList<>();
		if (sources != null) {
			targets.addAll(StreamSupport.stream(sources.spliterator(), false).
					filter(Objects::nonNull).
					map(this::convert).
					collect(Collectors.toList()));
		}

		return targets;
	}

}
