package io.github.eventiful.plugin;

import gnu.trove.map.hash.TObjectIntHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bukkit.Statistic;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(2)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StatisticStorageBenchmark {
    private Statistic statistic;
    private HashMap<Statistic, Integer> hashMap;
    private IdentityHashMap<Statistic, Integer> identityHashMap;
    private Object2IntOpenHashMap<Statistic> object2IntOpenHashMap;
    private TObjectIntHashMap<Statistic> tObjectIntHashMap;

    @Setup
    public void setUp() {
        statistic = Statistic.JUMP;
        hashMap = new HashMap<>();
        identityHashMap = new IdentityHashMap<>();
        object2IntOpenHashMap = new Object2IntOpenHashMap<>();
        tObjectIntHashMap = new TObjectIntHashMap<>();
    }

    @TearDown
    public void tearDown() {
        statistic = null;
        hashMap = null;
        identityHashMap = null;
        object2IntOpenHashMap = null;
        tObjectIntHashMap = null;
    }
    
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInHashMap_1() {
        hashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInHashMap_10() {
        for (int i = 0; i < 10; i++)
            hashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInHashMap_100() {
        for (int i = 0; i < 100; i++)
            hashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            hashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            hashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInIdentityHashMap_1() {
        identityHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInIdentityHashMap_10() {
        for (int i = 0; i < 10; i++)
            identityHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInIdentityHashMap_100() {
        for (int i = 0; i < 100; i++)
            identityHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInIdentityHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            identityHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInIdentityHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            identityHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInObject2IntOpenHashMap_1() {
        object2IntOpenHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInObject2IntOpenHashMap_10() {
        for (int i = 0; i < 10; i++)
            object2IntOpenHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInObject2IntOpenHashMap_100() {
        for (int i = 0; i < 100; i++)
            object2IntOpenHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInObject2IntOpenHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            object2IntOpenHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInObject2IntOpenHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            object2IntOpenHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInTIntObjectMap_1() {
        tObjectIntHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInTIntObjectMap_10() {
        for (int i = 0; i < 10; i++)
            tObjectIntHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInTIntObjectMap_100() {
        for (int i = 0; i < 100; i++)
            tObjectIntHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInTIntObjectMap_1000() {
        for (int i = 0; i < 1000; i++)
            tObjectIntHashMap.put(statistic, statistic.hashCode());
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void EntryInsertion_StatisticKeyIntegerValue_InsertedInTIntObjectMap_10000() {
        for (int i = 0; i < 10000; i++)
            tObjectIntHashMap.put(statistic, statistic.hashCode());
    }

    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromHashMap_1() {
        hashMap.get(statistic);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromHashMap_10() {
        for (int i = 0; i < 10; i++)
            hashMap.get(statistic);
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromHashMap_100() {
        for (int i = 0; i < 100; i++)
            hashMap.get(statistic);
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            hashMap.get(statistic);
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            hashMap.get(statistic);
    }

    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromIdentityHashMap_1() {
        identityHashMap.get(statistic);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromIdentityHashMap_10() {
        for (int i = 0; i < 10; i++)
            identityHashMap.get(statistic);
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromIdentityHashMap_100() {
        for (int i = 0; i < 100; i++)
            identityHashMap.get(statistic);
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromIdentityHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            identityHashMap.get(statistic);
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromIdentityHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            identityHashMap.get(statistic);
    }

    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromObject2IntOpenHashMap_1() {
        object2IntOpenHashMap.get(statistic);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromObject2IntOpenHashMap_10() {
        for (int i = 0; i < 10; i++)
            object2IntOpenHashMap.get(statistic);
    }

    @OperationsPerInvocation(100)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromObject2IntOpenHashMap_100() {
        for (int i = 0; i < 100; i++)
            object2IntOpenHashMap.get(statistic);
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromObject2IntOpenHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            object2IntOpenHashMap.get(statistic);
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromObject2IntOpenHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            object2IntOpenHashMap.get(statistic);
    }

    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromTObjectIntHashMap_1() {
        tObjectIntHashMap.get(statistic);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromTObjectIntHashMap_10() {
        for (int i = 0; i < 10; i++)
            tObjectIntHashMap.get(statistic);
    }

    @OperationsPerInvocation(10)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromTObjectIntHashMap_100() {
        for (int i = 0; i < 100; i++)
            tObjectIntHashMap.get(statistic);
    }

    @OperationsPerInvocation(1000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromTObjectIntHashMap_1000() {
        for (int i = 0; i < 1000; i++)
            tObjectIntHashMap.get(statistic);
    }

    @OperationsPerInvocation(10000)
    @Benchmark
    public void ValueRetrieval_StatisticKey_ReturnsValueFromTObjectIntHashMap_10000() {
        for (int i = 0; i < 10000; i++)
            tObjectIntHashMap.get(statistic);
    }
}
