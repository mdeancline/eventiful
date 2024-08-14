package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import gnu.trove.map.hash.TIntObjectHashMap;
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

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PerformanceTest {
    @Benchmark
    public void MapInsertion_ClassKeyObjectValue_InsertedInHashMap(final MapState state) {
        state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap(final MapState state) {
        state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapInsertion_HashKeyObjectValue_InsertedInHashMap(final MapState state) {
        state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapInsertion_HashKeyObjectValue_InsertedInTIntObjectMap(final MapState state) {
        state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void EventDispatch_EventBus_HandledByEventListeners(final EventifulState state) {
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    public void EventDispatch_EventBus_HandledByEventListenersPreCached(final EventifulState state) {
        state.classScanner.scanSubtypes(Event.class, ignored -> {});
        state.eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Benchmark
    @Timeout(time = 1)
    public void EventDispatch_PluginManagerMock_HandledByListeners(final BukkitState state) {
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
    @Timeout(time = 1)
    public void ListenerRegistration_PluginManagerMock_AssociatesWithAbsoluteEventTypes(final BukkitState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @Benchmark
    @Timeout(time = 1)
    public void ListenerRegistration_PluginManagerMock_HandledByProxyComponents(final EventifulState state) {
        state.mockServer.getPluginManager().registerEvents(new MockListener(), state.mockPlugin);
    }

    @Test
    public void runBenchmarks() throws RunnerException {
        final Options options = new OptionsBuilder().include(getClass().getName()).build();
        new Runner(options).run();
    }

    @State(Scope.Benchmark)
    public static class MapState {
        HashMap<Class<?>, Object> classesAndObjects;
        IdentityHashMap<Class<?>, Object> classesAndObjectsIdentity;
        HashMap<Integer, Object> hashCodesAndObjects;
        TIntObjectHashMap<Object> hashCodesAndObjectsTrove;

        @Setup
        public void setUp() {
            classesAndObjects = new HashMap<>();
            classesAndObjectsIdentity = new IdentityHashMap<>();
            hashCodesAndObjects = new HashMap<>();
            hashCodesAndObjectsTrove = new TIntObjectHashMap<>();
        }

        @TearDown
        public void tearDown() {
            classesAndObjects = null;
            classesAndObjectsIdentity = null;
            hashCodesAndObjects = null;
            hashCodesAndObjectsTrove = null;
        }
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
