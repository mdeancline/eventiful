package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.listener.EventListener;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractEventRegistration<T extends Event> implements EventRegistration<T> {
    private final Class<T> eventType;
    private final EventListener<T> listener;
    private final Plugin plugin;

    @Override
    public final EventPriority getPriority() {
        return listener.getPriority();
    }
}
