package io.github.eventiful.plugin.reflect;

import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public final class CacheableListenerReflector implements ListenerReflector {
    private final Map<Listener, Collection<ListenerMethod<?>>> methods = new IdentityHashMap<>();
    private final ListenerReflector source;

    public CacheableListenerReflector(final ListenerReflector source) {
        this.source = source;
    }

    @Override
    public Collection<ListenerMethod<?>> getMethods(final Listener listener) {
        return methods.computeIfAbsent(listener, source::getMethods);
    }
}
