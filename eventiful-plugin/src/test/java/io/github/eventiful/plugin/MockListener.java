package io.github.eventiful.plugin;

import io.github.eventiful.MockEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Getter
@RequiredArgsConstructor
public class MockListener implements Listener {
    private int invocationCount;

    @EventHandler
    public void handleMockEvent(final MockEvent event) {
        invocationCount++;
    }
}
