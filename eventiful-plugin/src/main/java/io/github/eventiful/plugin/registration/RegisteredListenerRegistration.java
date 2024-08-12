package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.listener.EventListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.RegisteredListener;

public class RegisteredListenerRegistration<T extends Event> extends AbstractEventRegistration<T> {
    public RegisteredListenerRegistration(final Class<T> type, final RegisteredListener listener) {
        super(type, new RegisteredListenerAdapter<>(listener), listener.getPlugin());
    }

    private static class RegisteredListenerAdapter<T extends Event> implements EventListener<T> {
        private final RegisteredListener listener;

        private RegisteredListenerAdapter(final RegisteredListener listener) {
            this.listener = listener;
        }

        @Override
        public void handle(final T event) {
            try {
                listener.callEvent(event);
            } catch (final EventException e) {
                throw new io.github.eventiful.api.exception.EventException(e);
            }
        }

        @Override
        public EventPriority getPriority() {
            return listener.getPriority();
        }
    }
}
