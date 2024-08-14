package io.github.eventiful.plugin;

import gnu.trove.map.hash.TIntObjectHashMap;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MapPerformanceTest {
    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInHashMap_1(final MapState state) {
        state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.classesAndObjects.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap_1(final MapState state) {
        state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_ClassKeyObjectValue_InsertedInIdentityHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.classesAndObjectsIdentity.put(Object.class, new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInHashMap_1(final MapState state) {
        state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.hashCodesAndObjects.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInTIntObjectMap_1(final MapState state) {
        state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInTIntObjectMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInTIntObjectMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInTIntObjectMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapEntryInsertion_HashKeyObjectValue_InsertedInTIntObjectMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.hashCodesAndObjectsTrove.put(Object.class.hashCode(), new Object());
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromHashMap_1(final MapState state) {
        state.classesAndObjects.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.classesAndObjects.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.classesAndObjects.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.classesAndObjects.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.classesAndObjects.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromIdentityHashMap_1(final MapState state) {
        state.classesAndObjectsIdentity.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromIdentityHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.classesAndObjectsIdentity.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromIdentityHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.classesAndObjectsIdentity.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromIdentityHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.classesAndObjectsIdentity.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_ClassKey_ReturnsValueFromIdentityHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.classesAndObjectsIdentity.get(Object.class);
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromHashMap_1(final MapState state) {
        state.hashCodesAndObjects.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromHashMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.hashCodesAndObjects.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromHashMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.hashCodesAndObjects.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromHashMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.hashCodesAndObjects.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromHashMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.hashCodesAndObjects.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromTIntObjectMap_1(final MapState state) {
        state.hashCodesAndObjectsTrove.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromTIntObjectMap_10(final MapState state) {
        for (int i = 0; i < 10; i++)
            state.hashCodesAndObjectsTrove.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromTIntObjectMap_100(final MapState state) {
        for (int i = 0; i < 100; i++)
            state.hashCodesAndObjectsTrove.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromTIntObjectMap_1000(final MapState state) {
        for (int i = 0; i < 1000; i++)
            state.hashCodesAndObjectsTrove.get(Object.class.hashCode());
    }

    @Benchmark
    public void MapValueRetrieval_HashKey_ReturnsValueFromTIntObjectMap_10000(final MapState state) {
        for (int i = 0; i < 10000; i++)
            state.hashCodesAndObjectsTrove.get(Object.class.hashCode());
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
}
