package io.github.eventiful.plugin.reflect;

import io.github.eventiful.MockEvent;
import io.github.eventiful.plugin.MockHandlerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReflectionAccessTest {
    private ReflectionAccess reflectionAccess;
    private MockEvent mockEvent;
    private MockHandlerList mockHandlers;

    @Before
    public void setUp() {
        reflectionAccess = new UnsafeReflectionAccess();
        mockEvent = new MockEvent("Testing 1 2 3");
        mockHandlers = new MockHandlerList();
    }

    @After
    public void tearDown() {
        mockEvent = null;
        mockHandlers = null;
        reflectionAccess = null;
    }

    @Test
    public void GetFieldValue_ReflectionAccess_SuccessfulRetrieval() throws NoSuchFieldException {
        final String message = "Testing 1 2 3";
        final Object fieldValue = reflectionAccess.getObject(mockEvent.getMessageField(), mockEvent);
        assertEquals(message, fieldValue);
    }

    @Test
    public void GetFieldValueStatic_ReflectionAccess_SuccessfulRetrieval() throws NoSuchFieldException {
        final Object fieldValue = reflectionAccess.getObject(MockEvent.getHandlerListField());
        assertNotNull(fieldValue);
        assertTrue(fieldValue instanceof MockHandlerList);
    }

    @Test
    public void SetFieldValue_ReflectionAccess_SuccessfulWrite() throws NoSuchFieldException {
        final String newMessage = "Testing 3 2 1";
        reflectionAccess.setObject(mockEvent.getMessageField(), newMessage, mockEvent);
        assertEquals(newMessage, mockEvent.getMessage());
    }

    @Test
    public void SetFieldValueStatic_ReflectionAccess_SuccessfulWrite() throws NoSuchFieldException {
        reflectionAccess.setObject(MockEvent.getHandlerListField(), mockHandlers);
        assertEquals(mockHandlers, MockEvent.getHandlerList());
    }
}
