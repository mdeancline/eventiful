package io.github.eventiful.api;

import io.github.eventiful.api.exception.EventConcurrencyException;
import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Responsible for the registration and dispatching of events.
 *
 * @apiNote The only valid way to retrieve an {@code EventBus} instance is by loading the relevant service from the
 * {@link ServicesManager}.
 * <pre>
 * {@code
 * EventBus eventBus = Bukkit.getServicesManager().load(EventBus.class);
 * }
 * </pre>
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public interface EventBus {

    /**
     * Triggers the specified event, invoking all registered {@link EventListener}s associated with this event type.
     *
     * @param event The event to be triggered.
     * @throws EventConcurrencyException if the event's state is not safe for the current thread.
     * This exception is thrown to enforce thread safety, as most of the Bukkit API is not thread-safe.
     */
    void dispatch(@NotNull Event event);

    /**
     * Registers an {@link EventListener} for a specific event type, allowing the listener to listen for events of that type.
     *
     * @param <T>      The type of the event.
     * @param type     The class representing the event type to be watched.
     * @param listener The listener that will handle events of the specified type.
     * @return An {@link EventToken} linked to the registration of the listener.
     */
    <T extends Event> EventToken register(@NotNull Class<T> type, @NotNull EventListener<T> listener);

    /**
     * Unregisters an {@link EventListener} using the given token.
     *
     * @param token The token representing the listener registration to be removed.
     */
    void unregister(@NotNull EventToken token);
}
