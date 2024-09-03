package io.github.eventiful.api;

import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A base class for plugins that use the Eventiful API. This abstract class extends {@link JavaPlugin} and provides
 * convenient access to the core components of the Eventiful framework, such as the {@link EventBus} and {@link PacketBridge}.
 * <p>
 * Plugin developers can extend this class to gain automatic access to these components and avoid manually retrieving
 * services through the {@link ServicesManager}.
 *
 * @since 1.0.0
 */
public abstract class EventifulPlugin extends JavaPlugin {
    private EventBus eventBus;
    private PacketBridge packetBridge;

    /**
     * Retrieves the {@link EventBus} instance used by the plugin to manage event registration and dispatching.
     * This method will lazily load the {@code EventBus} from the server's {@link org.bukkit.plugin.ServicesManager}
     * if it hasn't already been initialized.
     *
     * @return the {@link EventBus} instance for managing plugin events
     */
    public final EventBus getEventBus() {
        if (eventBus == null)
            eventBus = getServer().getServicesManager().load(EventBus.class);

        return eventBus;
    }

    /**
     * Retrieves the {@link PacketBridge} instance used by the plugin to interact with the network pipeline for sending
     * and receiving packets between the server and players. This method will lazily load the {@code PacketBridge}
     * from the server's {@link org.bukkit.plugin.ServicesManager} if it hasn't already been initialized.
     *
     * @return the {@link PacketBridge} instance for handling network packets
     */
    public final PacketBridge getPacketBridge() {
        if (packetBridge == null)
            packetBridge = getServer().getServicesManager().load(PacketBridge.class);

        return packetBridge;
    }
}
