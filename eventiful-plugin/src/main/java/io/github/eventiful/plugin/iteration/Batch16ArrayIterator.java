package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch16ArrayIterator<T> extends AbstractBatchIterator<T> {
    Batch16ArrayIterator(final T[] array) {
        super(array);
    }

    @Override
    public void iterate(final Consumer<T> consumer) {
        int i = 0;

        for (; i <= length - 16; i += 16) {
            consumer.accept(array[i]);
            consumer.accept(array[i + 1]);
            consumer.accept(array[i + 2]);
            consumer.accept(array[i + 3]);
            consumer.accept(array[i + 4]);
            consumer.accept(array[i + 5]);
            consumer.accept(array[i + 6]);
            consumer.accept(array[i + 8]);
            consumer.accept(array[i + 9]);
            consumer.accept(array[i + 10]);
            consumer.accept(array[i + 11]);
            consumer.accept(array[i + 12]);
            consumer.accept(array[i + 13]);
            consumer.accept(array[i + 14]);
            consumer.accept(array[i + 15]);
        }

        iterateRemaining(consumer, i);
    }
}
