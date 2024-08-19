package io.github.eventiful.api.event.server;

import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the server has completed loading, either during initial startup or after a reload.
 *
 * @since 1.0.0
 */
public class ServerLoadEvent extends ServerEvent {
    private final LoadType type;

    @ApiStatus.Internal
    public ServerLoadEvent(@NotNull final LoadType type) {
        this.type = type;
    }

    /**
     * Retrieves the specific context in which the server was loaded.
     *
     * @return The corresponding {@link LoadType}.
     */
    @NotNull
    public LoadType getType() {
        return type;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    /**
     * Represents the different contexts in which the server load event can occur.
     */
    public enum LoadType {

        /**
         * Indicates that the server has completed loading during its initial startup.
         * This occurs when the server is first started and all resources are being loaded for the first time.
         */
        STARTUP,

        /**
         * Indicates that the server has completed loading after a reload operation.
         * This occurs when the server is reloaded, usually to apply changes or reload resources without restarting the entire server.
         */
        RELOAD,

        /**
         * Indicates that the server has finished loading, typically as the final stage of the loading process.
         * This state can apply to both initial startup and reload operations.
         */
        FINISHED
    }
}
