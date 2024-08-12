package io.github.eventiful.plugin.reflect;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public interface ListenerMethod<T extends Event> {
    void invoke(T event);

    Class<T> getParameterType();

    EventHandler getAnnotation();
}
