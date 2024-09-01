package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import io.github.eventiful.api.EventBus;
import lombok.experimental.Delegate;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EventBusPluginManagerMock extends PluginManagerMock {
    @Delegate
    private final PluginManagerMock source;
    private final EventBus eventBus;

    public EventBusPluginManagerMock(@NotNull final ServerMock server, final PluginManagerMock source, final EventBus eventBus) {
        //noinspection UnstableApiUsage
        super(server);
        this.source = source;
        this.eventBus = eventBus;
    }

    @Override
    public void callEvent(@NotNull final Event event) {
        eventBus.dispatch(event);
    }
}
