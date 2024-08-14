package io.github.eventiful.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A convenient abstraction for custom {@link Cancellable} {@link Event}s, simplifying the implementation of
 * cancellation logic.
 *
 * @since 1.0.0
 */
public abstract class CancellableEvent extends Event implements Cancellable {
    private boolean cancelled;

    @ApiStatus.Internal
    @NotNull
    @Override
    public final HandlerList getHandlers() {
        return new HandlerList();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
