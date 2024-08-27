package io.github.eventiful;

import io.github.eventiful.api.listener.EventListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

@RequiredArgsConstructor
public class MockEventListener<T extends Event> implements EventListener<T> {
    private final EventPriority priority;

    @Getter
    private int invocationCount;

    public MockEventListener() {
        this(EventPriority.NORMAL);
    }

    @Override
    public void handle(final T event) {
        invocationCount++;
    }

    @Override
    public EventPriority getPriority() {
        return priority;
    }
}
