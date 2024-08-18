package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

abstract class AbstractBatchIterator<T> implements BatchIterator<T> {
    protected final T[] array;
    protected final int length;

    protected AbstractBatchIterator(final T[] array) {
        this.array = array;
        length = array.length;
    }

    protected final void iterateRemaining(final Consumer<T> consumer, int i) {
        for (; i < length; i++)
            consumer.accept(array[i]);
    }
}
