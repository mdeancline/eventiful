package io.github.eventiful.plugin;

import io.github.eventiful.api.EventBus;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class EventBusAdapter extends AbstractRegisteredListener {
    private final EventBus eventBus;

    public EventBusAdapter(final EventBus eventBus, final Plugin plugin) {
        super(EventPriority.NORMAL, plugin);
        this.eventBus = eventBus;
    }

    @Override
    public void callEvent(final @NotNull Event event) {
        eventBus.dispatch(event);
    }
}
