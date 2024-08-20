package io.github.eventiful.plugin;

import io.github.eventiful.api.EventToken;
import io.github.eventiful.api.exception.EventConcurrencyException;
import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.registration.EventRegistration;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventRegistration;
import io.github.eventiful.plugin.scanner.ClassScanner;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Setter
@RequiredArgsConstructor
public class EventBusImpl implements ServerEventBus {
    private static final EventListener<?>[] DEFAULT_CACHE = new EventListener[0];

    private final Map<Class<?>, Channel<Event>> channels = new Object2ObjectOpenHashMap<>();
    private final ClassScanner classScanner;
    private final EventLogger logger;
    private final EventTokenProvider tokenProvider;
    private final Plugin plugin;
    @Setter
    private boolean serverLoaded;

    @Override
    public void dispatch(@NotNull final Event event) {
        if (event.isAsynchronous() == Bukkit.isPrimaryThread()) {
            final String messageTemplate = "Attempted to trigger %s on the %s thread";
            final String message = String.format(messageTemplate, event.getEventName(), Thread.currentThread().getName());
            throw new EventConcurrencyException(message);
        }

        final Class<? extends Event> type = event.getClass();
        publishToChannel(event, type);
        classScanner.scanSupertypesExcludingObject(type, scannedSupertype -> publishToChannel(event, scannedSupertype));
    }

    private void publishToChannel(final Event event, final Class<?> type) {
        final Channel<Event> channel = channels.get(type);
        if (channel != null)
            channel.dispatch(event);
    }

    @Override
    public <T extends Event> EventToken register(@NotNull final Class<T> type, @NotNull final EventListener<T> listener) {
        return register(new SimpleEventRegistration<>(type, listener, plugin));
    }

    @Override
    public EventToken register(final EventRegistration<?> registration) {
        if (!plugin.equals(registration.getPlugin()) && registration.getEventType().isAnnotationPresent(Deprecated.class))
            logger.logDeprecation(registration);

        return registerAsTyped(registration);
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> EventToken registerAsTyped(final EventRegistration<T> registration) {
        final Channel<T> channel = (Channel<T>) channels.computeIfAbsent(registration.getEventType(), k
                -> new Channel<>(tokenProvider));
        return channel.register(registration);
    }

    @Override
    public void unregister(@NotNull final EventToken token) {
        channels.get(token.getType()).unregister(token);
    }

    @Override
    public boolean isRegistered(@NotNull final EventToken token) {
        final Channel<?> channel = channels.get(token.getType());
        return channel != null && channel.isRegistered(token);
    }

    @SuppressWarnings("unchecked")
    private class Channel<T extends Event> {
        private final Map<EventToken, EventListener<T>> ownedListeners = new Object2ObjectOpenHashMap<>();
        private final Map<EventPriority, List<EventListener<T>>> orderedListeners = new EnumMap<>(EventPriority.class);
        private final EventTokenProvider tokenProvider;
        private volatile EventListener<T>[] iterationCache = (EventListener<T>[]) DEFAULT_CACHE;
        private int size;

        private Channel(final EventTokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;

            for (final EventPriority value : EventPriority.values())
                orderedListeners.put(value, new ObjectArrayList<>());
        }

        public void dispatch(final T event) {
            for (final EventListener<T> listener : iterationCache)
                listener.handle(event);
        }

        public synchronized EventToken register(final EventRegistration<T> registration) {
            final EventToken token = tokenProvider.createToken(registration);
            final EventListener<T> listener = registration.getListener();
            final EventPriority priority = registration.getPriority();

            final List<EventListener<T>> listeners = orderedListeners.get(priority);
            listeners.add(registration.getListener());
            ownedListeners.put(token, listener);
            updateIterationCache(true);

            return token;
        }

        public synchronized void unregister(final EventToken token) {
            final EventListener<T> listener = ownedListeners.remove(token);
            if (listener == null)
                throw new EventRegistrationException("No EventListener under that EventToken is registered");

            orderedListeners.get(token.getPriority()).remove(listener);
            updateIterationCache(false);
        }

        private void updateIterationCache(final boolean increment) {
            final Runnable task = createUpdateIterationCacheTask(increment);

            if (serverLoaded)
                CompletableFuture.runAsync(task);
            else
                task.run();
        }

        @SuppressWarnings("unchecked")
        private Runnable createUpdateIterationCacheTask(final boolean increment) {
            return () -> {
                int i = 0;
                iterationCache = new EventListener[increment ? ++size : --size];

                for (final Map.Entry<EventPriority, List<EventListener<T>>> entry : orderedListeners.entrySet())
                    for (final EventListener<T> listener : entry.getValue())
                        iterationCache[i++] = listener;
            };
        }

        public boolean isRegistered(final EventToken token) {
            return ownedListeners.containsKey(token);
        }
    }
}
