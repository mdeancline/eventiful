package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch8Array<E> extends AbstractBatchArray<E> {
    Batch8Array(final E[] source) {
        super(source);
    }

    @Override
    public void forEach(final Consumer<? super E> consumer) {
        int i = 0;

        for (; i <= length - 8; i += 8) {
            consumer.accept(source[i]);
            consumer.accept(source[i + 1]);
            consumer.accept(source[i + 2]);
            consumer.accept(source[i + 3]);
            consumer.accept(source[i + 4]);
            consumer.accept(source[i + 5]);
            consumer.accept(source[i + 6]);
            consumer.accept(source[i + 7]);
        }

        forEachRemaining(consumer, i);
    }
}
