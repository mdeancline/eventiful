package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch32Array<E> extends AbstractBatchArray<E> {
    Batch32Array(final E[] source) {
        super(source);
    }

    @Override
    public void forEach(final Consumer<? super E> consumer) {
        int i = 0;

        for (; i <= length - 32; i += 32) {
            consumer.accept(source[i]);
            consumer.accept(source[i + 1]);
            consumer.accept(source[i + 2]);
            consumer.accept(source[i + 3]);
            consumer.accept(source[i + 4]);
            consumer.accept(source[i + 5]);
            consumer.accept(source[i + 6]);
            consumer.accept(source[i + 7]);
            consumer.accept(source[i + 8]);
            consumer.accept(source[i + 9]);
            consumer.accept(source[i + 10]);
            consumer.accept(source[i + 11]);
            consumer.accept(source[i + 12]);
            consumer.accept(source[i + 13]);
            consumer.accept(source[i + 14]);
            consumer.accept(source[i + 15]);
            consumer.accept(source[i + 16]);
            consumer.accept(source[i + 17]);
            consumer.accept(source[i + 18]);
            consumer.accept(source[i + 19]);
            consumer.accept(source[i + 20]);
            consumer.accept(source[i + 21]);
            consumer.accept(source[i + 22]);
            consumer.accept(source[i + 23]);
            consumer.accept(source[i + 24]);
            consumer.accept(source[i + 25]);
            consumer.accept(source[i + 26]);
            consumer.accept(source[i + 27]);
            consumer.accept(source[i + 28]);
            consumer.accept(source[i + 29]);
            consumer.accept(source[i + 30]);
            consumer.accept(source[i + 31]);
        }

        forEachRemaining(consumer, i);
    }
}
