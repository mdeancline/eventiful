package io.github.eventiful.plugin.reflect;

import java.lang.reflect.Field;
import java.util.Collection;

public interface ReflectionAccess {
    void setObject(Field field, Object value);

    void setObject(Field field, Object value, Object holder);

    Object getObject(Field field);

    Object getObject(Field field, Object holder);

    Collection<Field> getAllDeclaringFields(Object holder);
}
