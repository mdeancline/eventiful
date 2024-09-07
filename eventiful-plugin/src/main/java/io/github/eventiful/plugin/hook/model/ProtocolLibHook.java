package io.github.eventiful.plugin.hook.model;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.server.PacketEvent;
import io.github.eventiful.api.event.server.PacketReceiveEvent;
import io.github.eventiful.api.event.server.PacketSendEvent;
import io.github.eventiful.plugin.hook.PluginHook;
import lombok.AllArgsConstructor;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public class ProtocolLibHook implements PluginHook {
    private final EventBus eventBus;
    private final Plugin plugin;

    @Override
    public void setup() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin) {
            @Override
            public void onPacketReceiving(final com.comphenix.protocol.events.PacketEvent event) {
                handle(event, new PacketReceiveEvent(event.getPacket(), event.getPacketType(), event.getPlayer()));
            }

            @Override
            public void onPacketSending(final com.comphenix.protocol.events.PacketEvent event) {
                handle(event, new PacketSendEvent(event.getPacket(), event.getPacketType(), event.getPlayer()));
            }

            private void handle(final com.comphenix.protocol.events.PacketEvent event, final PacketEvent serverEvent) {
                eventBus.dispatch(serverEvent);
                event.setCancelled(serverEvent.isCancelled());
            }
        });
    }

    @Override
    public String getName() {
        return "ProtocolLib";
    }
}
