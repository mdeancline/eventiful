package io.github.eventiful.plugin;

import io.github.eventiful.api.EventToken;
import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.registration.EventRegistration;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
class EventChannel<T extends Event> {
    private final Map<EventToken, EventListener<T>> ownedListeners = new Object2ObjectOpenHashMap<>();
    private final Map<EventPriority, List<EventListener<T>>> orderedListeners = new EnumMap<>(EventPriority.class);
    private final EventTokenProvider tokenProvider;
    private volatile EventListener<T>[] iterationCache;
    private int size;

    EventChannel(final EventTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;

        for (final EventPriority value : EventPriority.values())
            orderedListeners.put(value, new ObjectArrayList<>());
    }

    public void dispatch(final T event) {
        for (final EventListener<T> listener : iterationCache)
            listener.handle(event);
    }

    public EventToken register(final EventRegistration<T> registration) {
        final EventToken token = tokenProvider.createToken(registration);
        final EventListener<T> listener = registration.getListener();
        final EventPriority priority = registration.getPriority();

        final List<EventListener<T>> listeners = orderedListeners.get(priority);
        listeners.add(registration.getListener());
        ownedListeners.put(token, listener);
        refreshIterationCache(true);

        return token;
    }

    public void unregister(final EventToken token) {
        final EventListener<T> listener = ownedListeners.remove(token);
        if (listener == null)
            throw new EventRegistrationException("No EventListener under that EventToken is registered");

        orderedListeners.get(token.getPriority()).remove(listener);
        refreshIterationCache(false);
    }

    private void refreshIterationCache(final boolean increment) {
        CompletableFuture.runAsync(() -> {
            int i = 0;
            iterationCache = new EventListener[increment ? ++size : --size];

            for (final Map.Entry<EventPriority, List<EventListener<T>>> entry : orderedListeners.entrySet())
                for (final EventListener<T> listener : entry.getValue())
                    iterationCache[i++] = listener;
        });
    }
}
