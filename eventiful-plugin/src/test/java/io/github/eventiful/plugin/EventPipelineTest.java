package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import io.github.eventiful.plugin.registration.SimpleEventRegistration;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import org.bukkit.event.EventPriority;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class EventPipelineTest {
    private MockPlugin mockPlugin;
    private EventChannel<MockEvent> mockChannel;

    @Before
    public void setUp() {
        MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin();
        mockChannel = new EventChannel<>(new SimpleEventTokenProvider());
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
        mockChannel = null;
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokenAndByPriority() {
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(), mockPlugin));
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority() {
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.HIGHEST), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.LOWEST), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.HIGH), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.HIGH), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.HIGH), mockPlugin));
        mockChannel.register(new SimpleEventRegistration<>(MockEvent.class, new MockEventListener(EventPriority.LOW), mockPlugin));
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriorityThenIterates() {
        ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority();
        mockChannel.dispatch(new MockEvent("Testing 1 2 3"));
    }

    @Ignore
    @Test
    public void runBenchmark() throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(EventPipelineBenchmark.class.getName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}
