package com.hurynovich.data_storage.cache;

import java.util.List;

public interface Cache<K, V> {

	V get(K key);

	V update(K key, V value);

	List<V> getAll();

	void invalidate(K key);

}
