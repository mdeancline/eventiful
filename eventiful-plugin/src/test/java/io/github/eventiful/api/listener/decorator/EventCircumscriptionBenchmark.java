package io.github.eventiful.api.listener.decorator;

import io.github.eventiful.ExtendedMockEvent;
import io.github.eventiful.MockEvent;
import io.github.eventiful.MockEventListener;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.plugin.TestUtils;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Fork(2)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class EventCircumscriptionBenchmark {
    @Benchmark
    public void ListenerDispatch_EventExclusion_HandlesIfNotExcludedType(final ExclusionDispatchState state) {
        state.eventBus.dispatch(new ExtendedMockEvent());
    }

    @Benchmark
    public void ListenerDispatch_EventInclusion_HandlesIfNotExcludedType(final InclusionDispatchState state) {
        state.eventBus.dispatch(new ExtendedMockEvent());
    }

    @Benchmark
    public void ListenerDispatch_IdentityEventExclusion_HandlesIfNotExcludedType(final IdentityExclusionDispatchState state) {
        state.eventBus.dispatch(new ExtendedMockEvent());
    }

    @Benchmark
    public void ListenerDispatch_IdentityEventInclusion_HandlesIfNotExcludedType(final IdentityInclusionDispatchState state) {
        state.eventBus.dispatch(new ExtendedMockEvent());
    }

    @State(Scope.Benchmark)
    public static class ExclusionDispatchState {
        EventBus eventBus;

        @Setup
        public void setUp() {
            eventBus = TestUtils.createServerEventBusImpl();
            eventBus.register(MockEvent.class, new EventExclusion<>(new MockEventListener(), ExtendedMockEvent.class));
        }

        @TearDown
        public void tearDown() {
            eventBus = null;
        }
    }

    @State(Scope.Benchmark)
    public static class InclusionDispatchState {
        EventBus eventBus;

        @Setup
        public void setUp() {
            eventBus = TestUtils.createServerEventBusImpl();
            eventBus.register(MockEvent.class, new EventInclusion<>(new MockEventListener(), ExtendedMockEvent.class));
        }

        @TearDown
        public void tearDown() {
            eventBus = null;
        }
    }

    @State(Scope.Benchmark)
    public static class IdentityExclusionDispatchState {
        EventBus eventBus;

        @Setup
        public void setUp() {
            eventBus = TestUtils.createServerEventBusImpl();
            eventBus.register(MockEvent.class, new IdentityEventExclusion<>(new MockEventListener(), ExtendedMockEvent.class));
        }

        @TearDown
        public void tearDown() {
            eventBus = null;
        }
    }

    @State(Scope.Benchmark)
    public static class IdentityInclusionDispatchState {
        EventBus eventBus;

        @Setup
        public void setUp() {
            eventBus = TestUtils.createServerEventBusImpl();
            eventBus.register(MockEvent.class, new IdentityEventInclusion<>(new MockEventListener(), ExtendedMockEvent.class));
        }

        @TearDown
        public void tearDown() {
            eventBus = null;
        }
    }
}
