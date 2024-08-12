package io.github.eventiful.plugin.reflect;

import org.bukkit.event.Listener;

import java.util.Collection;

public interface ListenerReflector {
    Collection<ListenerMethod<?>> getMethods(Listener listener);
}
