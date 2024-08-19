package io.github.eventiful.api.listener.decorator;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.eventiful.ExtendedMockEvent;
import io.github.eventiful.MockEvent;
import io.github.eventiful.MockEventListener;
import io.github.eventiful.TestUtils;
import io.github.eventiful.api.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        final EventExclusion<MockEvent> exclusion = new EventExclusion<>(mockListener, ExtendedMockEvent.class);

        eventBus.register(MockEvent.class, exclusion).thenAccept(token -> {
            TestUtils.assertRegistration(eventBus, token);
            assertTrue(exclusion.circumscribedTypes.contains(ExtendedMockEvent.class));
            assertTrue(MockEvent.class.isAssignableFrom(ExtendedMockEvent.class));
            assertFalse(ExtendedMockEvent.class.isAssignableFrom(MockEvent.class));

            eventBus.dispatch(new MockEvent("Testing 7 8 9"));
            eventBus.dispatch(new ExtendedMockEvent("9 8 7 Testing"));

            assertEquals(1, mockListener.getInvocationCount());
        });
    }
}
