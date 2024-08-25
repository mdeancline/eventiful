package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
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
    public ServerEventBus createServerEventBusImpl() {
        if (!MockBukkit.isMocked())
            MockBukkit.mock();

        final EventLogger logger = new EventLogger(Logger.getGlobal());
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        final ClassScanner classScanner = new CacheableClassScanner(new ClassGraphScanner(new ClassGraph().enableAllInfo()));
        return new EventBusImpl(classScanner, logger, tokenProvider, MockBukkit.createMockPlugin());
    }

    public void assertRegistered(final EventBus eventBus, final EventToken token) {
        assertTrue(eventBus.isRegistered(token));
    }
}
