package io.github.eventiful.plugin.reflect;

import io.github.eventiful.api.event.CancellableEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class CancellableListenerReflector implements ListenerReflector {
    private final ListenerReflector source;

    public CancellableListenerReflector(final ListenerReflector source) {
        this.source = source;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<ListenerMethod<?>> getMethods(final Listener listener) {
        final Collection<ListenerMethod<?>> methods = new ArrayList<>();

        for (final ListenerMethod<?> method : source.getMethods(listener))
            methods.add(Cancellable.class.isAssignableFrom(method.getParameterType())
                    ? new Method<>((ListenerMethod<CancellableEvent>) method)
                    : method);

        return Collections.unmodifiableCollection(methods);
    }

    private static final class Method<T extends Event & Cancellable> implements ListenerMethod<T> {
        private final ListenerMethod<T> source;

        private Method(final ListenerMethod<T> source) {
            this.source = source;
        }

        @Override
        public void invoke(final T event) {
            if (event.isCancelled() && getAnnotation().ignoreCancelled()) return;
            source.invoke(event);
        }

        @Override
        public Class<T> getParameterType() {
            return source.getParameterType();
        }

        @Override
        public EventHandler getAnnotation() {
            return source.getAnnotation();
        }

        @Override
        public String toString() {
            return source.toString();
        }
    }
}
