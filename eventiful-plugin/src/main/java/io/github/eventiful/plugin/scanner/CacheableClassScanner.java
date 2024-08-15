package io.github.eventiful.plugin.scanner;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@AllArgsConstructor
public final class CacheableClassScanner implements ClassScanner {
    private final Map<Class<?>, Class<?>[]> supertypes = new Object2ObjectOpenHashMap<>();
    private final Map<Class<?>, Class<?>[]> subtypes = new Object2ObjectOpenHashMap<>();
    private final ClassScanner scanner;

    @Override
    public void scanSupertypes(final Class<?> clazz, final Consumer<Class<?>> consumer) {
        final Class<?>[] preciseSupertypes = supertypes.get(clazz);

        if (preciseSupertypes != null)
            for (final Class<?> supertype : preciseSupertypes)
                consumer.accept(supertype);
        else {
            final ObjectArrayList<Class<?>> scannedTypes = ObjectArrayList.wrap(new Class<?>[0]);

            scanner.scanSupertypes(clazz, supertype -> {
                supertypes.put(supertype, scannedTypes.elements());
                scannedTypes.add(supertype);
                consumer.accept(supertype);
            });

            supertypes.put(clazz, scannedTypes.elements());
        }
    }

    @Override
    public void scanSubtypes(final Class<?> clazz, final Consumer<Class<?>> consumer) {
        final Class<?>[] preciseSubtypes = subtypes.get(clazz);

        if (preciseSubtypes != null)
            for (final Class<?> subtype : preciseSubtypes)
                consumer.accept(subtype);
        else {
            final Queue<Class<?>> scannedTypes = new ConcurrentLinkedQueue<>();

            scanner.scanSubtypes(clazz, subtype -> {
                scannedTypes.add(subtype);
                consumer.accept(subtype);
            });

            subtypes.put(clazz, scannedTypes.toArray(new Class[0]));

            while (!scannedTypes.isEmpty())
                subtypes.put(scannedTypes.poll(), scannedTypes.toArray(new Class[0]));
        }
    }
}
