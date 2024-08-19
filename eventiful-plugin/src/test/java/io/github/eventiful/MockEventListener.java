package io.github.eventiful;

import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventPriority;

@AllArgsConstructor
public class MockEventListener implements EventListener<MockEvent> {
    private final EventPriority priority;

    public MockEventListener() {
        this(EventPriority.NORMAL);
    }

    @Override
    public void handle(final MockEvent event) {
        TestUtils.logEvent(event, priority);
    }

    @Override
    public EventPriority getPriority() {
        return priority;
    }
}
