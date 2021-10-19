package com.hurynovich.data_storage.cache;

import java.util.Optional;

public interface Cache<K, V> {

	void store(K key, V value);

	Optional<V> get(K key);

	boolean contains(K key);

	void invalidate(K key);

}
