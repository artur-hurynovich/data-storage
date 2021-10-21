package com.hurynovich.data_storage.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MassProcessingUtils {

	public MassProcessingUtils() {
		throw new AssertionError();
	}

	public static <T, U> List<U> processQuietly(final @Nullable List<T> elements,
												final @NonNull Function<T, U> processingFunction) {
		final List<U> processedElements = new ArrayList<>();
		if (elements != null) {
			processedElements.addAll(elements.stream().
					filter(Objects::nonNull).
					map(processingFunction).
					filter(Objects::nonNull).
					collect(Collectors.toList()));
		}

		return processedElements;
	}

}
