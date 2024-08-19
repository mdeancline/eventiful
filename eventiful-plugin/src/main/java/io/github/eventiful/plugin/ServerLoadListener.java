package io.github.eventiful.plugin;

import io.github.eventiful.api.event.server.ServerLoadEvent;
import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class ServerLoadListener implements EventListener<ServerLoadEvent> {
    private final ServerEventBus eventBus;

    @Override
    public void handle(final ServerLoadEvent event) {
        eventBus.setServerLoaded(event.getType() == ServerLoadEvent.LoadType.FINISHED);
    }
}
