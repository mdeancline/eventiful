package io.github.eventiful.plugin;

import io.github.eventiful.api.event.CancellableEvent;
import lombok.experimental.UtilityClass;
import org.bukkit.event.EventPriority;

@UtilityClass
public class TestUtils {
    public void logEvent(final MockEvent event, final EventPriority priority) {
        System.out.println(event.getEventName() + " was called");
        logPriority(priority);
    }

    public void logEvent(final CancellableEvent event, final EventPriority priority) {
        System.out.printf("%s was called (cancelled = %s)", event.getEventName(), event.isCancelled());
        logPriority(priority);
    }

    private void logPriority(final EventPriority priority) {
        System.out.printf("Priority: %s (%s)", priority, priority.getSlot());
    }
}
