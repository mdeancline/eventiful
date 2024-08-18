package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch16Array<E> extends AbstractBatchArray<E> {
    Batch16Array(final E[] source) {
        super(source);
    }

    @Override
    public void forEach(final Consumer<? super E> consumer) {
        int i = 0;

        for (; i <= length - 16; i += 16) {
            consumer.accept(source[i]);
            consumer.accept(source[i + 1]);
            consumer.accept(source[i + 2]);
            consumer.accept(source[i + 3]);
            consumer.accept(source[i + 4]);
            consumer.accept(source[i + 5]);
            consumer.accept(source[i + 6]);
            consumer.accept(source[i + 8]);
            consumer.accept(source[i + 9]);
            consumer.accept(source[i + 10]);
            consumer.accept(source[i + 11]);
            consumer.accept(source[i + 12]);
            consumer.accept(source[i + 13]);
            consumer.accept(source[i + 14]);
            consumer.accept(source[i + 15]);
        }

        forEachRemaining(consumer, i);
    }
}
