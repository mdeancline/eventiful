package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

@UtilityClass
public class TestUtils {
    public ServerEventBus newServerEventBusImpl() {
        if (!MockBukkit.isMocked())
            MockBukkit.mock();

        return newServerEventBusImplDirect();
    }

    private static @NotNull EventBusImpl newServerEventBusImplDirect() {
        final EventLogger logger = new EventLogger(Logger.getGlobal());
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        final ClassScanner classScanner = new CacheableClassScanner(new ClassGraphScanner(new ClassGraph().enableAllInfo()));
        return new EventBusImpl(classScanner, logger, tokenProvider, MockBukkit.createMockPlugin());
    }

    public ServerMock newServerMockWithEventBus(final Consumer<EventBus> eventBusConsumer) {
        final EventBusReference eventBusReference = new EventBusReference();

        final ServerMock serverMock = MockBukkit.mock(new ServerMock() {
            @Override
            public @NotNull PluginManagerMock getPluginManager() {
                return eventBusReference.hasReference()
                        ? new EventBusPluginManagerMock(this, super.getPluginManager(), eventBusReference)
                        : super.getPluginManager();
            }
        });

        final EventBus eventBus = newServerEventBusImplDirect();
        eventBusConsumer.accept(eventBus);
        eventBusReference.setReference(eventBus);
        return serverMock;
    }

    public void assertRegistered(final EventBus eventBus, final EventToken token) {
        assertTrue(eventBus.isRegistered(token));
    }

    public static String newRandomString() {
        return "";
    }
}
