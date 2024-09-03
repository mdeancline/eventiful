package io.github.eventiful.plugin.io;

import io.github.eventiful.api.event.server.Packet;
import io.github.eventiful.api.event.server.PacketScope;
import io.github.eventiful.api.event.server.PacketState;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class InterceptedPacket implements Packet {
    @Getter
    private final Object handle;
    private final ReflectionAccess reflectionAccess;

    @Override
    public void write(final int index, final Object value) {
        reflectionAccess.setObject(index, value, handle);
    }

    @Override
    public Object get(final int index) {
        return reflectionAccess.getObject(index, handle);
    }

    @Override
    public PacketScope getScope() {
        return null;
    }

    @Override
    public PacketState getState() {
        return null;
    }

    @Override
    public String getName() {
        return handle.getClass().getName();
    }
}
