package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch4ArrayIterator<T> extends AbstractBatchIterator<T> {
    Batch4ArrayIterator(final T[] array) {
        super(array);
    }

    @Override
    public void iterate(final Consumer<T> consumer) {
        int i = 0;

        for (; i <= length - 4; i += 4) {
            consumer.accept(array[i]);
            consumer.accept(array[i + 1]);
            consumer.accept(array[i + 2]);
            consumer.accept(array[i + 3]);
        }

        iterateRemaining(consumer, i);
    }
}
