package io.github.eventiful.api.exception;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Thrown to indicate that an operation involving a {@link HandlerList} is not supported by Eventiful.
 *
 * <p>This exception is typically raised when attempting to access the handler list for {@link Event}s that do not
 * support it, such as certain custom events in Eventiful.</p>
 *
 * @since 1.0.0
 */
public class HandlerListUnsupportedException extends EventException {
    private static final long serialVersionUID = 8211352411812901364L;

    /**
     * Constructs a new {@code HandlerListUnsupportedException} with a default message indicating
     * that {@link HandlerList} operations are not supported by Eventiful.
     */
    public HandlerListUnsupportedException() {
        super("Eventiful does not support " + HandlerList.class.getName());
    }
}
