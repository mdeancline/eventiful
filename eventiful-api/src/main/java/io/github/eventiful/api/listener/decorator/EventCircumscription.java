package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * @param <T> {@inheritDoc}
 */
abstract class EventCircumscription<T extends Event> implements EventListener<T> {
    protected final Set<Class<?>> circumscribedTypes = Collections.newSetFromMap(new IdentityHashMap<>());
    protected final EventListener<T> source;

    @SafeVarargs
    EventCircumscription(final EventListener<T> source, final Class<? extends T>... circumscribedTypes) {
        if (circumscribedTypes.length == 0)
            throw new EventRegistrationException("Circumscribed Event types must not be empty");

        this.source = source;
        this.circumscribedTypes.addAll(Arrays.asList(circumscribedTypes));
    }

    @Override
    public final EventPriority getPriority() {
        return source.getPriority();
    }
}