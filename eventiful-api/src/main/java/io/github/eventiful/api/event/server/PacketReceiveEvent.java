package io.github.eventiful.api.event.server;

import io.github.eventiful.api.PacketBridge;
import io.github.eventiful.api.PacketStructure;
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
    public PacketReceiveEvent(@NotNull final PacketStructure packet, @NotNull final PacketBridge bridge, @NotNull final Player player) {
        super(packet, bridge, player);
    }
}
