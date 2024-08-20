package io.github.eventiful;

import be.seeseemelk.mockbukkit.MockPlugin;
import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
import io.github.eventiful.plugin.EventBusImpl;
import io.github.eventiful.plugin.EventLogger;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import lombok.experimental.UtilityClass;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

@UtilityClass
public class TestUtils {
    public EventBusImpl createEventBusImpl(final MockPlugin mockPlugin) {
        final EventLogger logger = new EventLogger(Logger.getGlobal());
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        final ClassScanner classScanner = new CacheableClassScanner(new ClassGraphScanner(new ClassGraph().enableAllInfo()));
        return new EventBusImpl(classScanner, logger, tokenProvider, mockPlugin);
    }

    public void assertRegistered(final EventBus eventBus, final EventToken token) {
        assertTrue(eventBus.isRegistered(token));
    }
}
