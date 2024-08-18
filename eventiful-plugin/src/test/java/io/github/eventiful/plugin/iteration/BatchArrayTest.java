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

public class BatchArrayTest {
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
    public void Iteration_Batch4Array_IteratesLength4() {
        testBatchArrayIteration(4, Batch4Array.class);
    }

    @Test
    public void Iteration_Batch8Array_IteratesLength6() {
        testBatchArrayIteration(6, Batch4Array.class);
    }

    @Test
    public void Iteration_Batch8Array_IteratesLength8() {
        testBatchArrayIteration(8, Batch8Array.class);
    }

    @Test
    public void Iteration_Batch8Array_IteratesLength10() {
        testBatchArrayIteration(10, Batch8Array.class);
    }

    private void testBatchArrayIteration(final int length, final Class<?> expectedClass) {
        final EventListener<MockEvent>[] array = TestUtils.copiesOf(new MockEventListener(), length);
        assertEquals(length, array.length);

        final Iterable<EventListener<MockEvent>> batchArray = BatchIterables.of(array);
        assertEquals(expectedClass, batchArray.getClass());

        batchArray.forEach(consumer);
    }

    @Ignore
    @Test
    public void runBenchmark() throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(BatchArrayBenchmark.class.getName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}
