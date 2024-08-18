package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

public interface BatchIterator<T> {
    void iterate(Consumer<T> consumer);
}
