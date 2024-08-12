package io.github.eventiful.plugin.scanner;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class ClassGraphScanner extends AbstractClassScanner {
    private final ClassGraph classGraph;

    @Override
    public void scanSubtypes(final Class<?> type, final Consumer<Class<?>> consumer) {
        try (final ScanResult result = classGraph.scan()) {
            for (final ClassInfo info : result.getSubclasses(type))
                consumer.accept(info.loadClass());
        }
    }
}
