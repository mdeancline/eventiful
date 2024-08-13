package io.github.eventiful.plugin.scanner;

import java.util.function.Consumer;

abstract class AbstractClassScanner implements ClassScanner {
    @Override
    public final void scanSupertypes(final Class<?> type, final Consumer<Class<?>> consumer) {
        Class<?> supertype = type;
        while (!(supertype == Object.class))
            consumer.accept(supertype = supertype.getSuperclass());
    }
}
