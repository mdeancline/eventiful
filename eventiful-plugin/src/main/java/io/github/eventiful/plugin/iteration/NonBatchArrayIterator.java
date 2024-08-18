package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class NonBatchArrayIterator<T> extends AbstractBatchIterator<T> {
    NonBatchArrayIterator(final T[] array) {
        super(array);
    }

    @Override
    public void iterate(final Consumer<T> consumer) {
        for (final T value : array)
            consumer.accept(value);
    }
}
