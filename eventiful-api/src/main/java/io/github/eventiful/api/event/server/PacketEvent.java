package io.github.eventiful.api.event.server;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.github.eventiful.api.event.NonOperableHandlerList;
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
 * @apiNote <a href="https://www.spigotmc.org/resources/protocollib.1997/">ProtocolLib</a> is required for this event
 * type to be dispatched.
 * @since 1.0.0
 */
public abstract class PacketEvent extends ServerEvent implements Cancellable {
    private final PacketContainer packet;
    private final PacketType type;
    private final Player player;
    private boolean cancel;

    protected PacketEvent(@NotNull final PacketContainer packet, @NotNull final PacketType type, @NotNull final Player player) {
        this.packet = packet;
        this.type = type;
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
     * Retrieves the {@link PacketContainer} representing the packet being intercepted.
     *
     * @return The {@link PacketContainer}.
     */
    public final PacketContainer getPacket() {
        return packet;
    }

    /**
     * Retrieves the {@link PacketType} of the packet being intercepted.
     *
     * @return The {@link PacketType}.
     */
    public final PacketType getType() {
        return type;
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
