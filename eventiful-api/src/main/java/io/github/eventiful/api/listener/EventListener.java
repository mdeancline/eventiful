package io.github.eventiful.api.listener;

import io.github.eventiful.api.EventBus;
import org.bukkit.event.*;

/**
 * Represents a high-level {@link Listener} that doesn't require the {@link EventHandler} annotation. It leverages
 * generics to enhance efficiency and performance, particularly when dealing with {@link Cancellable} events.
 *
 * @param <T> the type of event that this listener handles, extending {@link Event}
 * @apiNote Only one "event handler method" is supported via {@link EventListener#handle(Event)}. If an incoming event
 * is asynchronous, any associated {@code EventListener}s will be executed in a separate thread.
 * @see CancellableEventListener
 * @see EventBus
 * @since 1.0.0
 */
public interface EventListener<T extends Event> {

    /**
     * Processes the incoming {@code Event}.
     *
     * @param event the incoming {@code Event}
     */
    void handle(T event);

    /**
     * Retrieves the {@link EventPriority} at which this {@code EventListener} processes events.
     *
     * @return the priority
     */
    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }
}
