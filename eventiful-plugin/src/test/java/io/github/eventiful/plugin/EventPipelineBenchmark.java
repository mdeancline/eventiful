package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class EventPipelineBenchmark {
    @Benchmark
    public void EventDispatch_EventBus_HandledByEventListeners(final EventifulState state) {
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void EventDispatch_EventBus_HandledByEventListenersPreCached(final EventifulState state) {
        state.classScanner.scanSubtypes(Event.class, ignored -> {
        });
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void EventDispatch_PluginManagerMock_HandledByListeners(final BukkitState state) {
        state.mockServer.getPluginManager().callEvent(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes_1(final EventifulState state) {
        state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes_10(final EventifulState state) {
        for (int i = 0; i < 10; i++)
            state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes_100(final EventifulState state) {
        for (int i = 0; i < 100; i++)
            state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes_1000(final EventifulState state) {
        for (int i = 0; i < 1000; i++)
            state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes_10000(final EventifulState state) {
        for (int i = 0; i < 10000; i++)
            state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @Benchmark
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes_1(final BukkitState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes_10(final BukkitState state) {
        for (int i = 0; i < 10; i++)
            state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes_100(final BukkitState state) {
        for (int i = 0; i < 100; i++)
            state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes_1000(final BukkitState state) {
        for (int i = 0; i < 1000; i++)
            state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes_10000(final BukkitState state) {
        for (int i = 0; i < 10000; i++)
            state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @Benchmark
    public void ListenerRegistration_PluginManagerMock_HandledByProxyComponents(final EventifulState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @State(Scope.Benchmark)
    public static class EventifulState {
        ServerMock mockServer;
        MockPlugin mockPlugin;
        ClassScanner classScanner;
        EventBus eventBus;

        @Setup
        public void setUp() {
            mockServer = MockBukkit.mock();
            mockPlugin = MockBukkit.createMockPlugin();
            classScanner = new CacheableClassScanner(new ClassGraphScanner(new ClassGraph().enableAllInfo()));
            eventBus = createEventBus();
        }

        private @NotNull EventBus createEventBus() {
            final EventLogger logger = new EventLogger(Logger.getGlobal());
            final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
            return new EventBusImpl(classScanner, logger, tokenProvider, mockPlugin);
        }

        @TearDown
        public void tearDown() {
            MockBukkit.unmock();
            classScanner = null;
            eventBus = null;
        }
    }

    @State(Scope.Benchmark)
    public static class BukkitState {
        ServerMock mockServer;
        MockPlugin mockPlugin;

        @Setup
        public void setUp() {
            mockServer = MockBukkit.mock();
            mockPlugin = MockBukkit.createMockPlugin();
        }

        @TearDown
        public void tearDown() {
            MockBukkit.unmock();
        }
    }
}
