package io.github.eventiful.plugin.reflect;

import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@AllArgsConstructor
public class HandlerListInjector {
    private final ReflectionAccess reflectionAccess;

    public void inject(final HandlerList replacement, final Class<? extends Event> type) {
        for (final Field field : type.getDeclaredFields()) {
            if (field.getType() == HandlerList.class && Modifier.isStatic(field.getModifiers())) {
                final HandlerList original = (HandlerList) reflectionAccess.getObject(field);
                replacement.registerAll(Arrays.asList(original.getRegisteredListeners()));
                reflectionAccess.setObject(field, replacement);
            }
        }
    }
}
