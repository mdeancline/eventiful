package io.github.eventiful.plugin.reflect;

import io.github.eventiful.plugin.MockEvent;
import io.github.eventiful.plugin.MockHandlerList;
import org.junit.Test;

public class ReflectionAccessTest {
    @Test
    public void GetFieldValue_UnsafeReflectionAccess_RetrievalAccessSuccessful() throws NoSuchFieldException {
        testGetFieldValue(new UnsafeReflectionAccess());
    }

    @Test
    public void GetFieldValueStatic_UnsafeReflectionAccess_RetrievalAccessSuccessful() throws NoSuchFieldException {
        testGetFieldValueStatic(new UnsafeReflectionAccess());
    }

    @Test
    public void SetFieldValue_UnsafeReflectionAccess_WriteAccessSuccessful() throws NoSuchFieldException {
        testSetFieldValue(new UnsafeReflectionAccess());
    }

    @Test
    public void SetFieldValueStatic_UnsafeReflectionAccess_WriteAccessSuccessful() throws NoSuchFieldException {
        testSetFieldValueStatic(new UnsafeReflectionAccess());
    }

    private void testGetFieldValue(final ReflectionAccess reflectionAccess) throws NoSuchFieldException {
        final MockEvent mockEvent = new MockEvent("Testing 7 8 9");
        System.out.println("Field value: " + reflectionAccess.getObject(mockEvent.getMessageField(), mockEvent));
    }

    private void testGetFieldValueStatic(final ReflectionAccess reflectionAccess) throws NoSuchFieldException {
        System.out.println("Static field value: " + reflectionAccess.getObject(MockEvent.getHandlerListField()));
    }

    private void testSetFieldValue(final ReflectionAccess reflectionAccess) throws NoSuchFieldException {
        final MockEvent mockEvent = new MockEvent("Testing 1 2 3");
        System.out.println("Original message: " + mockEvent.getMessage());
        reflectionAccess.setObject(mockEvent.getMessageField(), "3 2 1 Testing", mockEvent);
        System.out.println("Replaced message: " + mockEvent.getMessage());
    }

    private static void testSetFieldValueStatic(final ReflectionAccess reflectionAccess) throws NoSuchFieldException {
        System.out.println("Original static field value: " + MockEvent.getHandlerList());
        reflectionAccess.setObject(MockEvent.getHandlerListField(), new MockHandlerList());
        System.out.println("Replaced static field value: " + MockEvent.getHandlerList());
    }
}
