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
public class BatchIterationBenchmark {
    private BatchIterator<EventListener<MockEvent>> batch4ArrayIterator;
    private BatchIterator<EventListener<MockEvent>> batch8ArrayIterator;
    private BatchIterator<EventListener<MockEvent>> batch16ArrayIterator;
    private BatchIterator<EventListener<MockEvent>> batch32ArrayIterator;
    private Consumer<EventListener<MockEvent>> iterationConsumer;

    @Setup
    public void setUp() {
        batch4ArrayIterator = new Batch4ArrayIterator<>(TestUtils.copiesOf(new MockEventListener(), 4));
        batch8ArrayIterator = new Batch8ArrayIterator<>(TestUtils.copiesOf(new MockEventListener(), 8));
        batch16ArrayIterator = new Batch16ArrayIterator<>(TestUtils.copiesOf(new MockEventListener(), 16));
        batch32ArrayIterator = new Batch32ArrayIterator<>(TestUtils.copiesOf(new MockEventListener(), 32));
        iterationConsumer = listener -> listener.handle(new MockEvent("Testing 1 2 3"));
    }

    @TearDown
    public void tearDown() {
        batch4ArrayIterator = null;
        batch8ArrayIterator = null;
        batch16ArrayIterator = null;
        iterationConsumer = null;
    }

    @OperationsPerInvocation
    @Benchmark
    public void ArrayIteration_Batch4ArrayIterator_PerformsOptimizedIteration() {
        batch4ArrayIterator.iterate(iterationConsumer);
    }
}
