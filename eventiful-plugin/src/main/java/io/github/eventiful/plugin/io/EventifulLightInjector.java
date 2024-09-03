package io.github.eventiful.plugin.io;

import com.fren_gor.lightInjector.LightInjector;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.server.*;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EventifulLightInjector extends LightInjector implements PacketStream {
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

        final Packet interceptedPacket = new InterceptedPacket(packet, reflectionAccess);
        return dispatchEvent(new ServerPacketReceiveEvent(interceptedPacket, this, player)) ? packet : null;
    }

    @Override
    protected @Nullable Object onPacketSendAsync(@Nullable final Player player, @NotNull final Channel channel, @NotNull final Object packet) {
        if (player == null)
            return packet;

        final PacketStream stream = Bukkit.getServicesManager().load(PacketStream.class);
        final Packet newPacket = Objects.requireNonNull(stream).newPacket("PacketPlayOutHealth");
        newPacket.write(2, 3);

        final Packet interceptedPacket = new InterceptedPacket(packet, reflectionAccess);
        return dispatchEvent(new ServerPacketSendEvent(interceptedPacket, this, player)) ? packet : null;
    }

    private boolean dispatchEvent(final ServerPacketEvent event) {
        eventBus.dispatch(event);
        return event.isCancelled();
    }

    @Override
    public void dispatch(final Packet packet) {
        for (final Player player : Bukkit.getOnlinePlayers())
            sendPacket(player, packet.getHandle());
    }

    @Override
    public void dispatch(final Packet packet, final Player player) {
        sendPacket(player, packet.getHandle());
    }

    @Override
    public Packet newPacket(final String name, final Object... parameters) {
        return new ConstructedPacket(reflectionAccess, name, parameters);
    }
}
