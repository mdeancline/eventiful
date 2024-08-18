package io.github.eventiful.plugin.iteration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchIterables {
    public <T> Iterable<T> of(final T[] array) {
        final int length = array.length;

        if (length >= 32)
            return new Batch32Array<>(array);
        else if (length >= 16)
            return new Batch16Array<>(array);
        else if (length >= 8)
            return new Batch8Array<>(array);
        else if (length >= 4)
            return new Batch4Array<>(array);
        else
            return new Batch1Array<>(array);
    }
}
