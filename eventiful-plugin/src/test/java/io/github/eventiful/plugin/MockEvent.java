package io.github.eventiful.plugin;

import io.github.eventiful.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Field;

@Getter
@AllArgsConstructor
public class MockEvent extends CancellableEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String message;

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
