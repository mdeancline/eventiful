package io.github.eventiful.plugin.reflect;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Map;

public final class CacheableListenerReflector implements ListenerReflector {
    private final Map<Listener, Collection<ListenerMethod<?>>> methods = new Object2ObjectOpenHashMap<>();
    private final ListenerReflector source;

    public CacheableListenerReflector(final ListenerReflector source) {
        this.source = source;
    }

    @Override
    public Collection<ListenerMethod<?>> getMethods(final Listener listener) {
        return methods.computeIfAbsent(listener, source::getMethods);
    }
}
