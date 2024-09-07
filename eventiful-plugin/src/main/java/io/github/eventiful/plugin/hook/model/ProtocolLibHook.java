package io.github.eventiful.plugin.hook.model;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.server.ServerPacketEvent;
import io.github.eventiful.api.event.server.ServerPacketReceiveEvent;
import io.github.eventiful.api.event.server.ServerPacketSendEvent;
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
            public void onPacketReceiving(final PacketEvent event) {
                handle(event, new ServerPacketReceiveEvent(event.getPacket(), event.getPacketType(), event.getPlayer()));
            }

            @Override
            public void onPacketSending(final PacketEvent event) {
                handle(event, new ServerPacketSendEvent(event.getPacket(), event.getPacketType(), event.getPlayer()));
            }

            private void handle(final PacketEvent event, final ServerPacketEvent serverEvent) {
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
