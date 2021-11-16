package com.hurynovich.data_storage.event;

import com.hurynovich.data_storage.event.model.Event;
import com.hurynovich.data_storage.model.AbstractDTO;
import org.springframework.lang.NonNull;

public interface EventHandler<T extends AbstractDTO<?>> {

	void handle(@NonNull Event<T> event);

}
