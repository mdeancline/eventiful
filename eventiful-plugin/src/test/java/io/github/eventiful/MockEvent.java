package io.github.eventiful;

import io.github.eventiful.api.event.PlainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Field;

@Getter
@Setter
@RequiredArgsConstructor
public class MockEvent extends PlainEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String message;
    private boolean cancelled;

    public MockEvent() {
        this("");
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public static Field getHandlerListField() throws NoSuchFieldException {
        return MockEvent.class.getDeclaredField("HANDLER_LIST");
    }

    public Field getMessageField() throws NoSuchFieldException {
        return getClass().getDeclaredField("message");
    }
}
