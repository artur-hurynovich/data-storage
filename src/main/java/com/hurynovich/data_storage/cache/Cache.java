package com.hurynovich.data_storage.cache;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface Cache<K, V> {

	void store(@NonNull K key, @NonNull V value);

	Optional<V> get(@NonNull K key);

	boolean contains(@NonNull K key);

	void invalidate(@NonNull K key);

}
