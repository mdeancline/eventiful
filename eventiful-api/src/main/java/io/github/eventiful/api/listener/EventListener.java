package io.github.eventiful.api.listener;

import io.github.eventiful.api.EventBus;
import org.bukkit.event.*;

/**
 * A high-level {@link Listener} that does not require the use of the {@link EventHandler} annotation.
 * This listener utilizes generics to improve efficiency and performance, especially when handling {@link Cancellable}
 * events.
 *
 * @param <T> the type of event this listener processes, which must extend {@link Event}
 * @apiNote This listener only supports a single "event handler method," defined in {@link EventListener#handle(Event)}.
 * If {@link Event#isAsynchronous()} returns {@code true} for an incoming {@link Event}, the associated
 * {@code EventListener}s will be executed in a separate thread.
 *
 * @see CancellableEventListener
 * @see EventBus
 * @since 1.0.0
 */
public interface EventListener<T extends Event> {

    /**
     * Processes the incoming {@code Event} within its synchronization state.
     *
     * @apiNote See the interface documentation for details on concurrent handling.
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
