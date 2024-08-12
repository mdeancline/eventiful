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
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Fork(2)
@Warmup(iterations = 2)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Timeout(time = 1)
public class PerformanceTest {
    @Benchmark
    public void EventDispatch_EventBus_HandledByListeners(final EventifulState state) {
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void EventDispatch_EventBus_HandledByListenersPreCached(final EventifulState state) {
        state.classScanner.scanSubtypes(Event.class, ignored -> {});
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void EventDispatch_PluginManager_MockedBehavior(final BukkitState state) {
        state.mockServer.getPluginManager().callEvent(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypes(final EventifulState state) {
        state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @Benchmark
    public void ListenerRegistration_EventBus_AssociatesWithDerivativeEventTypesPreCached(final EventifulState state) {
        state.classScanner.scanSubtypes(Event.class, ignored -> {});
        state.eventBus.register(MockEvent.class, new MockEventListener());
    }

    @Benchmark
    public void ListenerRegistration_PluginManager_MockedBehavior(final BukkitState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @Benchmark
    public void ListenerRegistration_PluginManager_ReplacedByProxyComponents(final EventifulState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @Test
    public void runBenchmarks() throws RunnerException {
        final Options options = new OptionsBuilder().include(getClass().getName()).build();
        new Runner(options).run();
    }

    @State(Scope.Benchmark)
    public static class EventifulState {
        private ServerMock mockServer;
        private MockPlugin mockPlugin;
        private ClassScanner classScanner;
        private EventBus eventBus;

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
        }
    }

    @State(Scope.Benchmark)
    public static class BukkitState {
        private ServerMock mockServer;
        private MockPlugin mockPlugin;

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
