package io.github.eventiful.plugin.io;

import com.fren_gor.lightInjector.LightInjector;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.PacketBridge;
import io.github.eventiful.api.PacketStructure;
import io.github.eventiful.api.event.server.PacketEvent;
import io.github.eventiful.api.event.server.PacketReceiveEvent;
import io.github.eventiful.api.event.server.PacketSendEvent;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EventifulLightInjector extends LightInjector implements PacketBridge {
    private final EventBus eventBus;
    private final ReflectionAccess reflectionAccess;

    public EventifulLightInjector(@NotNull final Plugin plugin, final EventBus eventBus, final ReflectionAccess reflectionAccess) {
        super(plugin);
        this.eventBus = eventBus;
        this.reflectionAccess = reflectionAccess;
    }

    @Override
    protected @Nullable Object onPacketReceiveAsync(@Nullable final Player player, @NotNull final Channel channel, @NotNull final Object packet) {
        if (player == null)
            return packet;

        final PacketStructure interceptedPacket = new InterceptedPacket(packet, reflectionAccess);
        return dispatchEvent(new PacketReceiveEvent(interceptedPacket, this, player)) ? packet : null;
    }

    @Override
    protected @Nullable Object onPacketSendAsync(@Nullable final Player player, @NotNull final Channel channel, @NotNull final Object packet) {
        if (player == null)
            return packet;

        final PacketStructure interceptedPacket = new InterceptedPacket(packet, reflectionAccess);
        return dispatchEvent(new PacketSendEvent(interceptedPacket, this, player)) ? packet : null;
    }

    private boolean dispatchEvent(final PacketEvent event) {
        eventBus.dispatch(event);
        return event.isCancelled();
    }

    @Override
    public void dispatch(final PacketStructure packet) {
        final Object handle = packet.getHandle();

        for (final Player player : Bukkit.getOnlinePlayers())
            sendPacket(player, handle);
    }

    @Override
    public void dispatch(final PacketStructure packet, final Player player) {
        sendPacket(player, packet.getHandle());
    }

    @Override
    public PacketStructure newPacket(final String simpleClassName) {
        return new ConstructedPacketStructure(simpleClassName);
    }

    @Override
    public PacketStructure newPacket(final byte id) {
        return null;
    }
}
