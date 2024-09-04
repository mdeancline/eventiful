package io.github.eventiful.plugin.network;

import io.github.eventiful.api.PacketContainer;
import io.github.eventiful.api.PacketDirection;
import io.github.eventiful.api.PacketState;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class InterceptedPacket implements PacketContainer {
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
    public PacketDirection getDirection() {
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
