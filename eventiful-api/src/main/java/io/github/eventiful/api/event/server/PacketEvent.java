package io.github.eventiful.api.event.server;

import io.github.eventiful.api.PacketBridge;
import io.github.eventiful.api.PacketStructure;
import io.github.eventiful.api.event.NonOperableHandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Minecraft packet event involving the server and a {@link Player}.
 *
 * @since 1.0.0
 */
public abstract class PacketEvent extends ServerEvent implements Cancellable {
    private final PacketStructure packet;
    private final PacketBridge bridge;
    private final Player player;
    private boolean cancel;

    protected PacketEvent(@NotNull final PacketStructure packet, @NotNull final PacketBridge bridge, @NotNull final Player player) {
        super(true);
        this.packet = packet;
        this.bridge = bridge;
        this.player = player;
    }

    @NotNull
    @Override
    @ApiStatus.Internal
    public final HandlerList getHandlers() {
        return NonOperableHandlerList.getInstance();
    }

    @Override
    public final void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public final boolean isCancelled() {
        return cancel;
    }

    /**
     * Retrieves the intercepted {@link PacketStructure}.
     *
     * @return the intercepted {@link PacketStructure}.
     */
    public final PacketStructure getPacket() {
        return packet;
    }

    /**
     * Retrieves the {@link PacketBridge} associated with this event.
     *
     * @return the {@link PacketBridge}.
     */
    public final PacketBridge getBridge() {
        return bridge;
    }

    /**
     * Retrieves the {@link Player} who is involved with the packet.
     *
     * @return The {@link Player} involved.
     */
    public final Player getPlayer() {
        return player;
    }
}
