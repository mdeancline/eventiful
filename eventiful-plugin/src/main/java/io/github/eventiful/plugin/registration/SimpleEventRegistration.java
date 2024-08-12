package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class SimpleEventRegistration<T extends Event> extends AbstractEventRegistration<T> {
    public SimpleEventRegistration(final Class<T> type, final EventListener<T> listener, final Plugin plugin) {
        super(type, listener, plugin);
    }
}
