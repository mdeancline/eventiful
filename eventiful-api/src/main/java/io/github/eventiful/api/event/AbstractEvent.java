package io.github.eventiful.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A base class for creating custom {@link Event} types without the need to manage a specific {@link HandlerList}.
 *
 * <p>This abstraction simplifies event creation by removing the requirement to store or maintain a {@link HandlerList},
 * which is not needed or supported by the Eventiful framework.</p>
 *
 * @since 1.0.0
 */
public abstract class AbstractEvent extends Event {
    @ApiStatus.Internal
    @NotNull
    @Override
    public final HandlerList getHandlers() {
        return NonOperableHandlerList.getInstance();
    }
}
