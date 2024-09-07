package io.github.eventiful.api.event.server;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the {@link Server} receives a packet from a {@link Player}.
 *
 * @since 1.0.0
 */
public class PacketReceiveEvent extends PacketEvent {

    @ApiStatus.Internal
    public PacketReceiveEvent(@NotNull final PacketContainer packet, @NotNull final PacketType type, @NotNull final Player player) {
        super(packet, type, player);
    }
}
