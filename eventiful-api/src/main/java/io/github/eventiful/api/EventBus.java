package io.github.eventiful.api;

import io.github.eventiful.api.exception.EventConcurrencyException;
import io.github.eventiful.api.listener.EventListener;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Responsible for the registration and dispatching of events.
 *
 * @apiNote To obtain a valid {@code PacketBridge} instance, you must load the service using the
 * {@link ServicesManager}.
 * <pre>
 * {@code
 * EventBus eventBus = Bukkit.getServicesManager().load(EventBus.class);
 * }
 * </pre>
 * @see EventifulPlugin
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
     * Registers an {@link EventListener} to handle events of the specified type.
     *
     * <br><br>
     * <b>Note:</b> The registration process will only maintain synchronous execution if the {@link Server} is not
     * fully loaded.
     *
     * @param type     The class representing the event type to be monitored.
     * @param listener The listener that will handle events of the specified type.
     * @return An {@link EventToken} that represents the registration of the listener.
     * @param <T>      The type of the event.
     */
    <T extends Event> EventToken register(@NotNull Class<T> type, @NotNull EventListener<T> listener);

    /**
     * Unregisters an {@link EventListener} using the provided token. Once unregistered, the listener
     * will no longer receive events.
     *
     * @param token The {@link EventToken} representing the registration of the listener that
     *              needs to be removed.
     */
    void unregister(@NotNull EventToken token);

    /**
     * Checks whether an {@link EventListener} is currently registered based on the associated {@link EventToken}.
     *
     * @param token The {@link EventToken} representing the listener registration to check.
     * @return {@code true} if the listener associated with the given token is currently registered;
     *         {@code false} otherwise.
     */
    boolean isRegistered(@NotNull EventToken token);
}
