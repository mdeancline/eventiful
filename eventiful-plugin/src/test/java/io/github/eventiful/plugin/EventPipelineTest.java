package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.eventiful.api.EventBus;
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
    private EventBus eventBus;

    @Before
    public void setUp() {
        MockBukkit.mock();
        eventBus = TestUtils.createEventBusImpl(MockBukkit.createMockPlugin());
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
        eventBus = null;
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokenAndByPriority() {
        eventBus.register(MockEvent.class, new MockEventListener());
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority() {
        eventBus.register(MockEvent.class, new MockEventListener());
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGHEST));
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.LOWEST));
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH));
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH));
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH));
        eventBus.register(MockEvent.class, new MockEventListener(EventPriority.LOW));
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriorityThenIterates() {
        ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority();
        eventBus.dispatch(new MockEvent("Testing 1 2 3"));
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
