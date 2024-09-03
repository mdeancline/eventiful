package io.github.eventiful.api.event;

import io.github.eventiful.api.exception.EventRegistrationException;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * A specialized {@link HandlerList} that is non-operable outside the controlled environment of the {@link PluginManager}.
 * It enforces restrictions on who can access its instance, ensuring that only the plugin manager can interact with it.
 *<br><br>
 * The {@link NonOperableHandlerList} serves as a security measure to prevent unauthorized event registrations or handler
 * manipulations since the Eventiful framework doesn't utilize {@link HandlerList}s.
 *
 * @since 1.0.0
 */
public final class NonOperableHandlerList extends HandlerList {
    private static final NonOperableHandlerList INSTANCE = new NonOperableHandlerList();
    private static final PluginManagerVerification VERIFICATION = new PluginManagerVerification();

    private NonOperableHandlerList() {
        super();
    }

    @ApiStatus.Internal
    public static NonOperableHandlerList getInstance() {
        if (!VERIFICATION.isFromPluginManager())
            throw new EventRegistrationException("Illegal NonOperableHandlerList access");

        return INSTANCE;
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
