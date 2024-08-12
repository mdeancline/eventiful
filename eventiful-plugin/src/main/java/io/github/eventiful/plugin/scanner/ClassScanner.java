package io.github.eventiful.plugin.scanner;

import java.util.function.Consumer;

public interface ClassScanner {
    void scanSupertypes(Class<?> clazz, Consumer<Class<?>> consumer);

    void scanSubtypes(Class<?> clazz, Consumer<Class<?>> consumer);
}
