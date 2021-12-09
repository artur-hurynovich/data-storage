package com.hurynovich.data_storage.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class TestReflectionUtils {

	private TestReflectionUtils() {
		throw new AssertionError();
	}

	public static void setField(final @NonNull Object target, final @NonNull String fieldName,
								final @Nullable Object fieldValue) {
		final Field field = ReflectionUtils.findField(target.getClass(), fieldName);
		if (field != null) {
			field.setAccessible(true);

			ReflectionUtils.setField(field, target, fieldValue);
		} else {
			throw new IllegalArgumentException("Field with name = '" + fieldName + "' not found");
		}
	}
}
