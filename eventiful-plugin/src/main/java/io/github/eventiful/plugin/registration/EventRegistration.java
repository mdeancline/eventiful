package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public interface EventRegistration<T extends Event> {
    Class<T> getEventType();

    EventListener<T> getListener();

    Plugin getPlugin();

    EventPriority getPriority();
}
