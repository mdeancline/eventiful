package io.github.eventiful.plugin.iteration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchIterators {
    private static final BatchIterator<?> EMPTY = consumer -> {
    };

    public <T> BatchIterator<T> of(final T[] array) {
        final int length = array.length;

        if (length >= 16)
            return new Batch16ArrayIterator<>(array);
        else if (length >= 8)
            return new Batch8ArrayIterator<>(array);
        else if (length >= 4)
            return new Batch4ArrayIterator<>(array);
        else
            return new NonBatchArrayIterator<>(array);
    }

    @SuppressWarnings("unchecked")
    public <T> BatchIterator<T> empty() {
        return (BatchIterator<T>) EMPTY;
    }
}

