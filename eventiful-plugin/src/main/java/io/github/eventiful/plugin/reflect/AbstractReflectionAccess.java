package io.github.eventiful.plugin.reflect;

import io.github.eventiful.api.exception.EventException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractReflectionAccess implements ReflectionAccess {
    @Override
    public Collection<Field> getAllDeclaringFields(final Object holder) {
        final List<Field> fields = new LinkedList<>();
        Class<?> superclass = holder.getClass();

        while (superclass != null) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        return fields;
    }

    @Override
    public Object getObject(final Field field) {
        try {
            field.setAccessible(true);
            return field.get(null);
        } catch (final IllegalAccessException e) {
            throw new EventException(e);
        }
    }

    @Override
    public Object getObject(final Field field, final Object holder) {
        try {
            field.setAccessible(true);
            return field.get(holder);
        } catch (final IllegalAccessException e) {
            throw new EventException(e);
        }
    }
}
