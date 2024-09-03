package io.github.eventiful.plugin.io;

import io.github.eventiful.api.event.server.Packet;
import io.github.eventiful.api.event.server.PacketScope;
import io.github.eventiful.api.event.server.PacketState;
import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import lombok.Getter;
import net.insprill.spigotutils.MinecraftVersion;
import net.insprill.spigotutils.ServerEnvironment;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

class ConstructedPacket implements Packet {
    private static final Class<?> NMS_BASE_PACKET_CLASS = getNmsBasePacketClass();

    @Getter
    private final Object handle;
    private final ReflectionAccess reflectionAccess;
    @Getter
    private final String name;
    @Getter
    private final PacketScope scope;
    @Getter
    private final PacketState state;

    public ConstructedPacket(final ReflectionAccess reflectionAccess, final String name, final Object... packetConstructorParams) {
        try {
            this.reflectionAccess = reflectionAccess;
            this.name = name;
            handle = createPacketHandle(name, packetConstructorParams);
            scope = name.contains("PlayIn") ? PacketScope.CLIENT : PacketScope.SERVER;
            state = null;
        } catch (final ReflectiveOperationException e) {
            throw new EventRegistrationException(e);
        }
    }

    private @NotNull Object createPacketHandle(final String minecraftPacketName, final Object[] parameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final Class<?> minecraftPacketClass = Class.forName(minecraftPacketName);
        if (!NMS_BASE_PACKET_CLASS.isAssignableFrom(minecraftPacketClass))
            throw new EventRegistrationException("Unrecognized packet name");

        final Class<?>[] parameterTypes = new Class[parameters.length];

        for (int i = 0; i < parameterTypes.length; i++)
            parameterTypes[i] = parameters[i].getClass();

        return minecraftPacketClass.getDeclaredConstructor(parameterTypes).newInstance(parameters);
    }

    private static Class<?> getNmsBasePacketClass() {
        try {
            final boolean isRelocated = MinecraftVersion.isAtLeast(MinecraftVersion.v1_20_5) && ServerEnvironment.isPaper();
            final String packageName = "net.minecraft.server" + (isRelocated
                    ? ""
                    : MinecraftVersion.getCurrentVersion().getDisplayName());
            return Class.forName(packageName + "Packet");
        } catch (final ClassNotFoundException e) {
            throw new EventRegistrationException(e);
        }
    }

    @Override
    public void write(final int index, final Object value) {
        reflectionAccess.setObject(index, value, handle);
    }

    @Override
    public Object get(final int index) {
        return reflectionAccess.getObject(index, handle);
    }
}
