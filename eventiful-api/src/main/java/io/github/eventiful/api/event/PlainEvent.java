package io.github.eventiful.api.event;

import io.github.eventiful.api.exception.EventRegistrationException;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
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
public abstract class PlainEvent extends Event {
    private static final PluginManagerVerification VERIFICATION = new PluginManagerVerification();
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        if (!VERIFICATION.isFromPluginManager())
            throw new EventRegistrationException("Illegal HandlerList access");

        return HANDLER_LIST;
    }

    @ApiStatus.Internal
    @NotNull
    @Override
    public final HandlerList getHandlers() {
        return getHandlerList();
    }

    private static class PluginManagerVerification extends SecurityManager {
        public boolean isFromPluginManager() {
            for (final Class<?> callerClass : getClassContext())
                if (PluginManager.class.isAssignableFrom(callerClass))
                    return true;

            return false;
        }
    }
}
