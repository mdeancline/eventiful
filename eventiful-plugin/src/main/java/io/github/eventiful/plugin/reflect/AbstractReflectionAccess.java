package io.github.eventiful.plugin.reflect;

import io.github.eventiful.api.exception.EventException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractReflectionAccess implements ReflectionAccess {
    @Override
    public Collection<Field> getAllDeclaringFields(final Class<?> clazz) {
        final List<Field> fields = new LinkedList<>();
        Class<?> superclass = clazz;

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

    @Override
    public Object getObject(final int fieldIndex, final Object holder) {
        return getAllDeclaringFields(holder.getClass()).toArray()[fieldIndex];
    }

    @Override
    public void setObject(final int fieldIndex, final Object value, final Object holder) {
        setObject(holder.getClass().getDeclaredFields()[fieldIndex], value, holder);
    }
}
