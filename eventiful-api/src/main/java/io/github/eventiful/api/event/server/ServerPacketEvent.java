package io.github.eventiful.api.event.server;

import io.github.eventiful.api.NonOperableHandlerList;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Minecraft packet event involving the {@link Server} and a {@link Player}.
 *
 * @since 1.0.0
 */
public abstract class ServerPacketEvent extends ServerEvent implements Cancellable {
    private final Packet packet;
    private final PacketStream stream;
    private final Player player;
    private boolean cancel;

    protected ServerPacketEvent(@NotNull final Packet packet, @NotNull final PacketStream stream, @NotNull final Player player) {
        super(true);
        this.packet = packet;
        this.stream = stream;
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
     * Retrieves the intercepted {@link Packet}.
     *
     * @return the intercepted {@link Packet}.
     */
    public final Packet getPacket() {
        return packet;
    }

    /**
     * Retrieves the {@link PacketStream} associated with this event.
     *
     * @return the {@link PacketStream}.
     */
    public final PacketStream getStream() {
        return stream;
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
