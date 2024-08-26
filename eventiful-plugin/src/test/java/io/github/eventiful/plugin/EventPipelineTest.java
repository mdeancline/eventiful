package io.github.eventiful.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import io.github.eventiful.MockEvent;
import io.github.eventiful.MockEventListener;
import io.github.eventiful.MockPlainListener;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.EventToken;
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
    private PluginManagerMock mockPluginManager;
    private MockPlugin mockPlugin;

    @Before
    public void setUp() {
        mockPluginManager = MockBukkit.mock().getPluginManager();
        mockPlugin = MockBukkit.createMockPlugin();
        eventBus = TestUtils.createServerEventBusImpl();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
        eventBus = null;
        mockPlugin = null;
        mockPluginManager = null;
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokenAndByPriority() {
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener()));
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority() {
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener()));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGHEST)));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.LOWEST)));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH)));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH)));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.HIGH)));
        assertRegistered(eventBus.register(MockEvent.class, new MockEventListener(EventPriority.LOW)));
    }

    @Test
    public void ListenerRegistration_PluginManagerMock_ExecutesMockBehavior() {
        mockPluginManager.registerEvents(new MockPlainListener(), mockPlugin);
    }

    @Test
    public void ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriorityThenIterates() {
        ListenerRegistration_EventChannel_AssociatesWithTokensAndByDifferentPriority();
        eventBus.dispatch(new MockEvent("Testing 1 2 3"));
    }

    private void assertRegistered(final EventToken token) {
        TestUtils.assertRegistered(eventBus, token);
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
