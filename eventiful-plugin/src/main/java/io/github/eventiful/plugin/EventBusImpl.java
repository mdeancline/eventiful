package io.github.eventiful.plugin;

import io.github.eventiful.api.EventToken;
import io.github.eventiful.api.exception.EventConcurrencyException;
import io.github.eventiful.api.listener.EventListener;
import io.github.eventiful.plugin.registration.EventRegistration;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventRegistration;
import io.github.eventiful.plugin.scanner.ClassScanner;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Setter
@RequiredArgsConstructor
public class EventBusImpl implements ServerEventBus {
    private final Map<Class<?>, EventChannel<Event>> channels = new Object2ObjectOpenHashMap<>();
    private final ClassScanner classScanner;
    private final EventLogger logger;
    private final EventTokenProvider tokenProvider;
    private final Plugin plugin;

    @Override
    public void dispatch(@NotNull final Event event) {
        final boolean isPrimaryThread = Bukkit.isPrimaryThread();

        if (event.isAsynchronous() && isPrimaryThread)
            throw new EventConcurrencyException(String.format("Attempted to asynchronously trigger %s on the main thread",
                    event.getEventName()));

        if (!event.isAsynchronous() && !isPrimaryThread)
            throw new EventConcurrencyException(String.format("Attempted to synchronously trigger %s on thread %s",
                    event.getEventName(), Thread.currentThread().getName()));

        final Class<? extends Event> type = event.getClass();
        publishToChannel(event, type);
        classScanner.scanSupertypes(type, scannedSupertype -> publishToChannel(event, scannedSupertype));
    }

    private void publishToChannel(final Event event, final Class<?> type) {
        final EventChannel<Event> channel = channels.get(type);
        if (channel != null) channel.dispatch(event);
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
        final EventChannel<T> channel = (EventChannel<T>) channels.computeIfAbsent(registration.getEventType(), k
                -> new EventChannel<>(tokenProvider));
        return channel.register(registration);
    }

    @Override
    public void unregister(@NotNull final EventToken token) {
        channels.get(token.getType()).unregister(token);
    }
}
