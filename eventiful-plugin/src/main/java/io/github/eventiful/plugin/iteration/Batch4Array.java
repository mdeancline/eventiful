package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch4Array<E> extends AbstractBatchArray<E> {
    Batch4Array(final E[] source) {
        super(source);
    }

    @Override
    public void forEach(final Consumer<? super E> consumer) {
        int i = 0;

        for (; i <= length - 4; i += 4) {
            consumer.accept(source[i]);
            consumer.accept(source[i + 1]);
            consumer.accept(source[i + 2]);
            consumer.accept(source[i + 3]);
        }

        forEachRemaining(consumer, i);
    }
}
