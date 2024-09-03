package io.github.eventiful.api.event.server;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the {@link Server} sends a packet to a {@link Player}.
 *
 * @since 1.0.0
 */
public class ServerPacketSendEvent extends ServerPacketEvent {

    @ApiStatus.Internal
    public ServerPacketSendEvent(@NotNull final Packet packet, @NotNull final PacketStream stream, @NotNull final Player player) {
        super(packet, stream, player);
    }
}
