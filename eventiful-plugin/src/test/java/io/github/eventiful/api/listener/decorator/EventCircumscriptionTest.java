package io.github.eventiful.api.listener.decorator;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.eventiful.ExtendedMockEvent;
import io.github.eventiful.MockEvent;
import io.github.eventiful.MockEventListener;
import io.github.eventiful.TestUtils;
import io.github.eventiful.api.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static org.junit.Assert.*;

public class EventCircumscriptionTest {
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
    public void ListenerDispatch_EventExclusion_HandlesIfNotExcludedType() {
        final MockEventListener mockListener = new MockEventListener();
        final EventCircumscription<MockEvent> circumscription = new EventExclusion<>(mockListener, ExtendedMockEvent.class);
        testEventDispatchWithCircumscription(circumscription, mockListener);
    }

    @Test
    public void ListenerDispatch_EventInclusion_HandlesIfNotExcludedType() {
        final MockEventListener mockListener = new MockEventListener();
        final EventCircumscription<MockEvent> circumscription = new EventInclusion<>(mockListener, ExtendedMockEvent.class);
        testEventDispatchWithCircumscription(circumscription, mockListener);
    }

    @Test
    public void ListenerDispatch_IdentityEventExclusion_HandlesIfNotExcludedType() {
        final MockEventListener mockListener = new MockEventListener();
        final EventCircumscription<MockEvent> circumscription = new IdentityEventExclusion<>(mockListener, ExtendedMockEvent.class);
        testEventDispatchWithCircumscription(circumscription, mockListener);
    }

    @Test
    public void ListenerDispatch_IdentityEventInclusion_HandlesIfNotExcludedType() {
        final MockEventListener mockListener = new MockEventListener();
        final EventCircumscription<MockEvent> circumscription = new IdentityEventInclusion<>(mockListener, ExtendedMockEvent.class);
        testEventDispatchWithCircumscription(circumscription, mockListener);
    }

    private void testEventDispatchWithCircumscription(final EventCircumscription<MockEvent> circumscription, final MockEventListener mockListener) {
        eventBus.register(MockEvent.class, circumscription).thenAccept(token -> {
            TestUtils.assertRegistration(eventBus, token);
            assertTrue(circumscription.circumscribedTypes.contains(ExtendedMockEvent.class));
            assertTrue(MockEvent.class.isAssignableFrom(ExtendedMockEvent.class));
            assertFalse(ExtendedMockEvent.class.isAssignableFrom(MockEvent.class));

            eventBus.dispatch(new MockEvent("Testing 7 8 9"));
            eventBus.dispatch(new ExtendedMockEvent("9 8 7 Testing"));

            assertEquals(1, mockListener.getInvocationCount());
        });
    }

    @Ignore
    @Test
    public void runBenchmark() throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(EventCircumscriptionBenchmark.class.getName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}
