package com.hurynovich.data_storage.converter.model;

import org.springframework.lang.NonNull;

import java.util.function.Function;

public class ArgDescriptor<T, U> {

	private final String name;

	private final Class<U> type;

	private final Function<T, U> valueFunction;

	public ArgDescriptor(final @NonNull String name, final @NonNull Class<U> type,
						 final @NonNull Function<T, U> valueFunction) {
		this.name = name;
		this.type = type;
		this.valueFunction = valueFunction;
	}

	public String getName() {
		return name;
	}

	public Class<U> getType() {
		return type;
	}

	public Function<T, U> getValueFunction() {
		return valueFunction;
	}

}
