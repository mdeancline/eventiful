package io.github.eventiful.plugin.io;

import io.github.eventiful.api.PacketDirection;
import io.github.eventiful.api.PacketState;
import io.github.eventiful.api.PacketStructure;
import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.api.exception.PacketException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import net.insprill.spigotutils.MinecraftVersion;
import net.insprill.spigotutils.ServerEnvironment;

import java.lang.reflect.Constructor;
import java.util.List;

@Getter
class ConstructedPacketStructure implements PacketStructure {
    private static final Object2ObjectMap<String, Class<?>> PACKET_CLASS_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<Class<?>, Constructor<?>[]> PACKET_CONSTRUCTOR_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Class<?> NMS_BASE_PACKET_CLASS;

    static {
        try {
            NMS_BASE_PACKET_CLASS = getNmsBasePacketClass();
        } catch (final ClassNotFoundException e) {
            throw new PacketException(e);
        }
    }

    private final Int2ObjectMap<Object> fieldData = new Int2ObjectOpenHashMap<>();
    @Getter(AccessLevel.NONE)
    private final Class<?> packetClass;
    private final PacketDirection direction;
    private final PacketState state;

    public ConstructedPacketStructure(final String simpleClassName) {
        try {
            packetClass = getPacketClass(simpleClassName);
            direction = simpleClassName.contains("PlayIn") ? PacketDirection.CLIENT : PacketDirection.SERVER;
            state = null;
        } catch (final ClassNotFoundException e) {
            throw new PacketException(e);
        }
    }

    @Override
    public void write(final int index, final Object value) {
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
            throw new PacketException("Insufficient packet field data upon handle creation");
        } catch (final ReflectiveOperationException e) {
            throw new PacketException(e);
        }
    }

    @Override
    public String getName() {
        return packetClass.getSimpleName();
    }

    private Object newPacketHandle() throws ReflectiveOperationException {
        if (!NMS_BASE_PACKET_CLASS.isAssignableFrom(packetClass))
            throw new EventRegistrationException("Unrecognized Packet name");

        final Constructor<?> selectedConstructor = findMatchingConstructor();
        if (selectedConstructor == null)
            throw new PacketException("Insufficient field data for packet: " + packetClass.getName());

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

    private static Class<?> getPacketClass(final String simpleClassName) throws ClassNotFoundException {
        Class<?> cachedPacketClass = PACKET_CLASS_CACHE.get(simpleClassName);
        if (cachedPacketClass == null) {
            cachedPacketClass = Class.forName(getProtocolPackage() + simpleClassName);
            PACKET_CLASS_CACHE.put(simpleClassName, cachedPacketClass);
        }

        return cachedPacketClass;
    }

    private static Class<?> getNmsBasePacketClass() throws ClassNotFoundException {
        final String protocolPackage = getProtocolPackage();
        final boolean isPackageRelocated = MinecraftVersion.isAtLeast(MinecraftVersion.v1_20_5) && ServerEnvironment.isPaper();
        final String packageName = protocolPackage + (isPackageRelocated
                ? protocolPackage
                : MinecraftVersion.getCurrentVersion().getDisplayName());
        return Class.forName(packageName + "PacketStructure");
    }

    private static String getProtocolPackage() {
        return "";
    }
}
