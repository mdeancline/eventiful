package io.github.eventiful.plugin.iteration;

import com.google.common.collect.Iterators;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

abstract class AbstractBatchArray<E> implements Iterable<E> {
    protected final E[] source;
    protected final int length;

    protected AbstractBatchArray(final E[] source) {
        this.source = source;
        length = source.length;
    }

    @NotNull
    @Override
    public final Iterator<E> iterator() {
        return Iterators.forArray(source);
    }

    protected final void forEachRemaining(final Consumer<? super E> consumer, int i) {
        for (; i < length; i++)
            consumer.accept(source[i]);
    }
}
