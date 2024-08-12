package io.github.eventiful.plugin;

import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.plugin.reflect.ReflectionAccess;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@AllArgsConstructor
class HandlerListInjector {
    private final ReflectionAccess reflectionAccess;

    void inject(final HandlerList handlers, final Class<? extends Event> type) {
        try {
            tryInject(handlers, type);
        } catch (final ReflectiveOperationException e) {
            throw new EventRegistrationException(e);
        }
    }

    private void tryInject(final HandlerList replacement, final Class<? extends Event> type) throws ReflectiveOperationException {
        for (final Field field : type.getDeclaredFields()) {
            if (field.getType() == HandlerList.class && Modifier.isStatic(field.getModifiers())) {
                final HandlerList original = (HandlerList) reflectionAccess.getObject(field);
                replacement.registerAll(Arrays.asList(original.getRegisteredListeners()));
                reflectionAccess.setObject(field, replacement);
            }
        }
    }
}
