package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch8ArrayIterator<T> extends AbstractBatchIterator<T> {
    Batch8ArrayIterator(final T[] array) {
        super(array);
    }

    @Override
    public void iterate(final Consumer<T> consumer) {
        int i = 0;

        for (; i <= length - 8; i += 8) {
            consumer.accept(array[i]);
            consumer.accept(array[i + 1]);
            consumer.accept(array[i + 2]);
            consumer.accept(array[i + 3]);
            consumer.accept(array[i + 4]);
            consumer.accept(array[i + 5]);
            consumer.accept(array[i + 6]);
            consumer.accept(array[i + 7]);
        }

        iterateRemaining(consumer, i);
    }
}
