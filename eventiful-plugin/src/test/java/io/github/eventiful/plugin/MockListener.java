package io.github.eventiful.plugin;

import io.github.eventiful.MockEvent;
import io.github.eventiful.TestUtils;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class MockListener implements Listener {
    @EventHandler
    public void handleMockEvent(final MockEvent event) {
        TestUtils.logEvent(event, EventPriority.NORMAL);
    }
}
