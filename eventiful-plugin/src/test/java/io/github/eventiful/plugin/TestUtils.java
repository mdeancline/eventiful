package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockPlugin;
import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.event.CancellableEvent;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import lombok.experimental.UtilityClass;
import org.bukkit.event.EventPriority;

import java.lang.reflect.Array;
import java.util.logging.Logger;

@UtilityClass
public class TestUtils {
    public void logEvent(final MockEvent event, final EventPriority priority) {
        System.out.print(event.getEventName() + " was called");
        logPriority(priority);
    }

    public void logEvent(final CancellableEvent event, final EventPriority priority) {
        System.out.printf("%s was called (cancelled = %s)", event.getEventName(), event.isCancelled());
        logPriority(priority);
    }

    private void logPriority(final EventPriority priority) {
        System.out.printf("; priority: %s (%s)\n", priority, priority.getSlot());
    }

    public EventBusImpl createEventBusImpl(final MockPlugin mockPlugin) {
        final EventLogger logger = new EventLogger(Logger.getGlobal());
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        final ClassScanner classScanner = new CacheableClassScanner(new ClassGraphScanner(new ClassGraph().enableAllInfo()));
        return new EventBusImpl(classScanner, logger, tokenProvider, mockPlugin);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] copiesOf(final T source, final int length) {
        final T[] spanned = (T[]) Array.newInstance(source.getClass(), length);
        for (int i = 0; i < length; i++)
            spanned[i] = source;

        return spanned;
    }
}
