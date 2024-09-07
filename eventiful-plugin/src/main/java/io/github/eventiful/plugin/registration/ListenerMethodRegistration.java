package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.reflect.ListenerMethod;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public class ListenerMethodRegistration<T extends Event> extends AbstractEventRegistration<T> {
    public ListenerMethodRegistration(final ListenerMethod<T> method, final Plugin plugin) {
        super(method.getParameterType(), new ListenerMethodAdapter<>(method), plugin);
    }

    @AllArgsConstructor
    private static class ListenerMethodAdapter<T extends Event> implements EventListener<T> {
        private final ListenerMethod<T> method;

        @Override
        public void handle(final T event) {
            method.invoke(event);
        }

        @Override
        public EventPriority getPriority() {
            return method.getAnnotation().priority();
        }
    }
}
