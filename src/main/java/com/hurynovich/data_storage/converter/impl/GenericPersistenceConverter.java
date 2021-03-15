package com.hurynovich.data_storage.converter.impl;

import com.hurynovich.data_storage.exception.ConverterException;
import com.hurynovich.data_storage.converter.PersistenceConverter;
import com.hurynovich.data_storage.model.Identified;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericPersistenceConverter<T extends Identified<V>, U extends Identified<V>, V extends Serializable>
		implements PersistenceConverter<T, U, V> {

	private static final String INSTANTIATION_EXCEPTION_MESSAGE = "Failed to instantiate class of type '%s'";

	private final String[] ignoreProperties;

	protected GenericPersistenceConverter() {
		ignoreProperties = new String[0];
	}

	protected GenericPersistenceConverter(final String[] ignoreProperties) {
		this.ignoreProperties = ignoreProperties;
	}

	@Override
	public U convert(final T source) {
		final U result;
		if (source != null) {
			result = instantiateResult();

			BeanUtils.copyProperties(source, result, ignoreProperties);
		} else {
			result = null;
		}

		return result;
	}

	private U instantiateResult() {
		final Class<U> resultClass = getResultClass();

		try {
			return resultClass.getDeclaredConstructor().newInstance();
		} catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new ConverterException(String.format(INSTANTIATION_EXCEPTION_MESSAGE, resultClass));
		}
	}

	protected abstract Class<U> getResultClass();

	@Override
	public List<U> convertAll(final Iterable<T> sources) {
		final List<U> result = new ArrayList<>();
		if (sources != null) {
			result.addAll(StreamSupport.stream(sources.spliterator(), false).
					filter(Objects::nonNull).
					map(this::convert).
					collect(Collectors.toList()));
		}

		return result;
	}

}
