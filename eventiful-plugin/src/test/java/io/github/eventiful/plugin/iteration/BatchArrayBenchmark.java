package io.github.eventiful.plugin.iteration;

import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.MockEvent;
import io.github.eventiful.plugin.MockEventListener;
import io.github.eventiful.plugin.TestUtils;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Fork(2)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Timeout(time = 5)
public class BatchArrayBenchmark {
    private Iterable<EventListener<MockEvent>> batch4Array;
    private Iterable<EventListener<MockEvent>> batch8Array;
    private Iterable<EventListener<MockEvent>> batch16Array;
    private Iterable<EventListener<MockEvent>> batch32Array;
    private Consumer<EventListener<MockEvent>> iterationConsumer;

    @Setup
    public void setUp() {
        batch4Array = new Batch4Array<>(TestUtils.copiesOf(new MockEventListener(), 4));
        batch8Array = new Batch8Array<>(TestUtils.copiesOf(new MockEventListener(), 8));
        batch16Array = new Batch16Array<>(TestUtils.copiesOf(new MockEventListener(), 16));
        batch32Array = new Batch32Array<>(TestUtils.copiesOf(new MockEventListener(), 32));
        iterationConsumer = listener -> listener.handle(new MockEvent("Testing 1 2 3"));
    }

    @TearDown
    public void tearDown() {
        batch4Array = null;
        batch8Array = null;
        batch16Array = null;
        iterationConsumer = null;
    }

    @OperationsPerInvocation
    @Benchmark
    public void Iteration_Batch4Array_PerformsOptimizedIteration() {
        batch4Array.forEach(iterationConsumer);
    }
}
