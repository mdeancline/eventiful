package io.github.eventiful.plugin.reflect;

import io.github.eventiful.api.exception.EventException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

// https://stackoverflow.com/a/71465198
public class UnsafeReflectionAccess extends AbstractReflectionAccess {
    private static final Unsafe THE_UNSAFE;

    static {
        try{
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            THE_UNSAFE = (Unsafe) unsafeField.get(null);
        } catch(final ReflectiveOperationException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void setObject(final Field field, final Object value) {
        final Object holder = THE_UNSAFE.staticFieldBase(field);
        final long fieldOffset = THE_UNSAFE.staticFieldOffset(field);
        THE_UNSAFE.putObject(holder, fieldOffset, value);
    }

    @Override
    public void setObject(final Field field, final Object value, final Object holder) {
        final long fieldOffset = THE_UNSAFE.objectFieldOffset(field);
        THE_UNSAFE.putObject(holder, fieldOffset, value);
    }

    @Override
    public void setObject(final int fieldIndex, final Object value, final Object holder) {
        setObject(holder.getClass().getDeclaredFields()[fieldIndex], value, holder);
    }
}
