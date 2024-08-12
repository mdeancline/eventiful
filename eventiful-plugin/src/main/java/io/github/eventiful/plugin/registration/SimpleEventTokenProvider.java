package io.github.eventiful.plugin.registration;

import io.github.eventiful.api.EventToken;
import io.github.eventiful.api.listener.EventListener;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.Objects;

public class SimpleEventTokenProvider implements EventTokenProvider {
    @Override
    public EventToken createToken(final EventRegistration<?> registration) {
        return new SimpleEventToken(registration.getEventType(), registration.getListener());
    }

    @AllArgsConstructor
    private static class SimpleEventToken implements EventToken {
        private final Class<? extends Event> type;
        private final EventPriority priority;
        private final int hashCode;

        private SimpleEventToken(final Class<? extends Event> type, final EventListener<?> listener) {
            this.type = type;
            priority = listener.getPriority();
            hashCode = Objects.hash(type, listener);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) return true;
            if (!(other instanceof SimpleEventToken)) return false;
            final SimpleEventToken that = (SimpleEventToken) other;
            return Objects.equals(type, that.type) && hashCode == that.hashCode;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public Class<? extends Event> getType() {
            return type;
        }

        @Override
        public EventPriority getPriority() {
            return priority;
        }
    }
}
