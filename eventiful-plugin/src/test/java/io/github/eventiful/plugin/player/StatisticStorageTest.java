package io.github.eventiful.plugin.player;

import org.junit.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StatisticStorageTest {
    @Test
    public void runBenchmark() throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(StatisticStorageBenchmark.class.getName())
                .build();
        new Runner(options).run();
    }
}
