package io.github.eventiful.api.event.server;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the {@link Server} sends a packet to a {@link Player}.
 *
 * @since 1.0.0
 */
public class PacketSendEvent extends PacketEvent {

    @ApiStatus.Internal
    public PacketSendEvent(@NotNull final PacketContainer packet, @NotNull final PacketType type, @NotNull final Player player) {
        super(packet, type, player);
    }
}
