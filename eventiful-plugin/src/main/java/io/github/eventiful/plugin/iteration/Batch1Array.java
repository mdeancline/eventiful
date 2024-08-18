package io.github.eventiful.plugin.iteration;

import java.util.function.Consumer;

class Batch1Array<E> extends AbstractBatchArray<E> {
    Batch1Array(final E[] array) {
        super(array);
    }

    @Override
    public void forEach(final Consumer<? super E> consumer) {
        forEachRemaining(consumer, 0);
    }
}
