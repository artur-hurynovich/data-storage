package com.hurynovich.data_storage.event;

import com.hurynovich.data_storage.event.model.Event;
import org.springframework.lang.NonNull;

public interface EventHandler<T> {

	void handle(@NonNull Event<T> event);
}
