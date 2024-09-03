package io.github.eventiful.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a bridge to the Minecraft protocol pipeline responsible for handling packet transmissions.
 *
 * @apiNote To obtain a valid {@code PacketBridge} instance, you must load the service using the
 * {@link ServicesManager}.
 * <pre>
 * {@code
 * PacketBridge bridge = Bukkit.getServicesManager().load(PacketBridge.class);
 * }
 * </pre>
 * @see EventifulPlugin
 * @since 1.0.0
 */
@ApiStatus.NonExtendable
public interface PacketBridge {

    /**
     * Sends a packet to all connected players.
     *
     * @param packet the packet to be sent to the players
     */
    void dispatch(PacketStructure packet);

    /**
     * Sends a packet directly to a specific player.
     * This is typically used for player-specific updates, such as
     * sending individual player inventories or position updates.
     *
     * @param packet the packet to be sent to the specified player
     * @param player the player who will receive the packet
     */
    void dispatch(PacketStructure packet, Player player);

    /**
     * Creates a new packet instance using the specified internal class name.
     * This is intended for packets that
     * will be used within the Minecraft server environment.
     * The class name should correspond to the simple name of
     * a class that extends {@code net.minecraft.server.Packet} (for earlier versions) or
     * {@code net.minecraft.network.protocol.Packet} (for later versions).
     * This allows for compatibility across
     * different versions of the Minecraft server.
     *
     * @param simpleClassName the simple name of the Minecraft packet class (e.g., {@code "PacketPlayOutHeldItemSlot"})
     * @return the newly created packet structure
     */
    PacketStructure newPacket(String simpleClassName);

    /**
     * Creates a new packet using a {@code byte} identifier.
     * This method is useful for handling packets that are not part of the native Minecraft protocol, such as those
     * introduced by mods.
     * While {@link PacketBridge#newPacket(String)}
     * is recommended for standard Minecraft packets, this method provides flexibility for custom packets.
     *
     * @param id the {@code byte} identifier for the custom packet
     * @return the newly created packet structure
     */
    PacketStructure newPacket(byte id);
}
