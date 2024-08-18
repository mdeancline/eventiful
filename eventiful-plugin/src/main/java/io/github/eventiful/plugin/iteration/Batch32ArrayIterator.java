package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch32ArrayIterator<T> extends AbstractBatchIterator<T> {
    Batch32ArrayIterator(final T[] array) {
        super(array);
    }

    @Override
    public void iterate(final Consumer<T> consumer) {
        int i = 0;

        for (; i <= length - 32; i += 32) {
            consumer.accept(array[i]);
            consumer.accept(array[i + 1]);
            consumer.accept(array[i + 2]);
            consumer.accept(array[i + 3]);
            consumer.accept(array[i + 4]);
            consumer.accept(array[i + 5]);
            consumer.accept(array[i + 6]);
            consumer.accept(array[i + 7]);
            consumer.accept(array[i + 8]);
            consumer.accept(array[i + 9]);
            consumer.accept(array[i + 10]);
            consumer.accept(array[i + 11]);
            consumer.accept(array[i + 12]);
            consumer.accept(array[i + 13]);
            consumer.accept(array[i + 14]);
            consumer.accept(array[i + 15]);
            consumer.accept(array[i + 16]);
            consumer.accept(array[i + 17]);
            consumer.accept(array[i + 18]);
            consumer.accept(array[i + 19]);
            consumer.accept(array[i + 20]);
            consumer.accept(array[i + 21]);
            consumer.accept(array[i + 22]);
            consumer.accept(array[i + 23]);
            consumer.accept(array[i + 24]);
            consumer.accept(array[i + 25]);
            consumer.accept(array[i + 26]);
            consumer.accept(array[i + 27]);
            consumer.accept(array[i + 28]);
            consumer.accept(array[i + 29]);
            consumer.accept(array[i + 30]);
            consumer.accept(array[i + 31]);
        }

        iterateRemaining(consumer, i);
    }
}
