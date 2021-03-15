package com.hurynovich.data_storage.model;

import java.io.Serializable;

public interface Identified<T extends Serializable> {

	T getId();

}
