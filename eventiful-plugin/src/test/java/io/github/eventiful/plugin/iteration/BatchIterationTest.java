package io.github.eventiful.plugin.iteration;

import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.MockEvent;
import io.github.eventiful.plugin.MockEventListener;
import io.github.eventiful.plugin.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class BatchIterationTest {
    private Consumer<EventListener<MockEvent>> consumer;

    @Before
    public void setUp() {
        consumer = listener -> listener.handle(new MockEvent("Testing 1 2 3"));
    }

    @After
    public void tearDown() {
        consumer = null;
    }

    @Test
    public void ArrayIteration_Partitioned4ArrayIterator_WithDifferentLengths() {
        testBatchArrayIterator(4, Batch4ArrayIterator.class);
        testBatchArrayIterator(6, Batch4ArrayIterator.class);
    }

    @Test
    public void ArrayIteration_Partitioned8ArrayIterator_WithDifferentLengths() {
        testBatchArrayIterator(8, Batch8ArrayIterator.class);
        testBatchArrayIterator(10, Batch8ArrayIterator.class);
    }

    private void testBatchArrayIterator(final int length, final Class<?> expectedIteratorClass) {
        final EventListener<MockEvent>[] array = TestUtils.copiesOf(new MockEventListener(), length);
        assertEquals(length, array.length);

        final BatchIterator<EventListener<MockEvent>> iterator = BatchIterators.of(array);
        assertEquals(expectedIteratorClass, iterator.getClass());

        iterator.iterate(consumer);
    }

    @Ignore
    @Test
    public void runBenchmark() throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(BatchIterationBenchmark.class.getName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}
