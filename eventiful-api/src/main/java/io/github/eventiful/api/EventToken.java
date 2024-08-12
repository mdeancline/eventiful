package io.github.eventiful.api;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a token that can be used to efficiently locate and unregister an {@link EventListener},
 * even if the listener is wrapped in multiple layers of abstraction.
 *
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public interface EventToken {

    /**
     * Retrieves the type of {@link Event} associated with this {@code EventToken}.
     *
     * @return the associated {@link Event} type
     */
    Class<? extends Event> getType();

    EventPriority getPriority();
}
