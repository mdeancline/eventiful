package io.github.eventiful.plugin.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

abstract class AbstractClassScanner implements ClassScanner {
    @Override
    public final void scanSupertypes(final Class<?> type, final Consumer<Class<?>> consumer) {
        final List<Class<?>> supertypes = new ArrayList<>();
        Class<?> supertype = type;

        while (!supertype.equals(Object.class))
            supertypes.add(supertype = supertype.getSuperclass());

        for (int i = supertypes.size(); i > 0; i--)
            consumer.accept(supertypes.get(i - 1));
    }
}
