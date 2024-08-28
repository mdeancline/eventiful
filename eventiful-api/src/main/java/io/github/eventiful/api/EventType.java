package io.github.eventiful.api;

import io.github.eventiful.api.event.SpecificEntityEvent;
import org.bukkit.entity.Llama;
import org.bukkit.event.Event;

import java.util.function.Predicate;

public class EventType<T extends Event> {
    private final Predicate<T> predicate;

    private EventType(final Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public static <T extends Event> EventType<T> of(final Class<T> rawType) {
        return new EventType<>(event -> event.getClass() == rawType);
    }

    public static <T extends Event> EventType<T> from(final T event) {

    }
}
