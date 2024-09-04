package io.github.eventiful.plugin.network;

import io.github.eventiful.api.PacketContainer;
import io.github.eventiful.api.PacketDirection;
import io.github.eventiful.api.PacketState;
import io.github.eventiful.api.exception.PacketCreationException;
import io.github.eventiful.api.exception.PacketException;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import io.github.eventiful.plugin.util.MinecraftVersions;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import net.insprill.spigotutils.MinecraftVersion;

import java.lang.reflect.Constructor;
import java.util.List;

@Getter
class ConstructedPacketContainer implements PacketContainer {
    private static final Object2ObjectMap<String, Class<?>> PACKET_CLASS_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<Class<?>, Constructor<?>[]> PACKET_CONSTRUCTOR_CACHE = new Object2ObjectOpenHashMap<>();
    private static final String PROTOCOL_PACKAGE_NAME;
    private static final Class<?> NMS_BASE_PACKET_INTERFACE;

    static {
        PROTOCOL_PACKAGE_NAME = getProtocolPackageName();
        NMS_BASE_PACKET_INTERFACE = getPacketClass("Packet");
    }

    private final Int2ObjectMap<Object> fieldData;
    @Getter(AccessLevel.NONE)
    private final Class<?> packetClass;
    private final PacketDirection direction;
    private final PacketState state;
    private final int maxSize;

    public ConstructedPacketContainer(final String simpleClassName, final ReflectionAccess reflectionAccess) {
        if (simpleClassName.equals(NMS_BASE_PACKET_INTERFACE.getSimpleName()))
            throw new PacketCreationException("Cannot directly create a new packet from the base internal Packet interface");

        packetClass = getCachedPacketClass(simpleClassName);
        maxSize = reflectionAccess.getAllDeclaringFields(packetClass).size();
        fieldData = new Int2ObjectOpenHashMap<>(maxSize);
        direction = simpleClassName.contains("PlayIn") ? PacketDirection.CLIENT : PacketDirection.SERVER;
        state = getStateFromProtocol();
    }

    @Override
    public void write(final int index, final Object value) {
        if (index >= maxSize)
            throw new PacketException("Write index is greater than field count");

        fieldData.put(index, value);
    }

    @Override
    public Object get(final int index) {
        return fieldData.get(index);
    }

    @Override
    public Object getHandle() {
        try {
            return newPacketHandle();
        } catch (final IllegalArgumentException e) {
            throw new PacketCreationException("Insufficient field data upon packet handle creation", e);
        } catch (final ReflectiveOperationException e) {
            throw new PacketCreationException(e);
        }
    }

    @Override
    public String getName() {
        return packetClass.getSimpleName();
    }

    private Object newPacketHandle() throws ReflectiveOperationException {
        if (!NMS_BASE_PACKET_INTERFACE.isAssignableFrom(packetClass))
            throw new PacketCreationException("Unrecognized Packet name");

        final Constructor<?> selectedConstructor = findMatchingConstructor();
        if (selectedConstructor == null)
            throw new PacketCreationException("Insufficient field data for packet handle: " + packetClass.getName());

        return selectedConstructor.newInstance(collectConstructorArguments(selectedConstructor));
    }

    private Constructor<?> findMatchingConstructor() {
        Constructor<?>[] constructors = PACKET_CONSTRUCTOR_CACHE.get(packetClass);

        if (constructors == null) {
            constructors = packetClass.getConstructors();
            PACKET_CONSTRUCTOR_CACHE.put(packetClass, packetClass.getConstructors());
        }

        for (final Constructor<?> constructor : constructors)
            if (isConstructorMatching(constructor))
                return constructor;

        return null;
    }

    private boolean isConstructorMatching(final Constructor<?> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length != fieldData.size())
            return false;

        for (final Class<?> parameterType : parameterTypes)
            for (final Object value : fieldData.values())
                if (parameterType.isInstance(value))
                    return true;

        return true;
    }

    private Object[] collectConstructorArguments(final Constructor<?> constructor) {
        final List<Object> initArgs = new ObjectArrayList<>(fieldData.size());
        final Class<?>[] parameterTypes = constructor.getParameterTypes();

        for (final Class<?> parameterType : parameterTypes) {
            for (final Object value : fieldData.values()) {
                if (parameterType.isInstance(value)) {
                    initArgs.add(value);
                    break;
                }
            }
        }

        return initArgs.toArray();
    }

    private static Class<?> getCachedPacketClass(final String simpleClassName) {
        return PACKET_CLASS_CACHE.computeIfAbsent(simpleClassName, key -> {
            final Class<?> packetClass = getPacketClass(simpleClassName);
            PACKET_CLASS_CACHE.put(simpleClassName, packetClass);
            return packetClass;
        });
    }

    private static Class<?> getPacketClass(final String simpleClassName) {
        try {
            return Class.forName(getProtocolPackageName() + simpleClassName);
        } catch (final ClassNotFoundException e) {
            throw new PacketException(e);
        }
    }

    private PacketState getStateFromProtocol() {
        return null;
    }

    private static String getProtocolPackageName() {
        return MinecraftVersion.isAtLeast(MinecraftVersion.v1_17_0)
                ? "net.minecraft.network.protocol"
                : "net.minecraft.server." + MinecraftVersions.getCraftBukkitVersion();
    }
}
