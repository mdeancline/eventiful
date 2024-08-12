package io.github.eventiful.plugin;

import io.github.eventiful.api.EventToken;
import io.github.eventiful.plugin.reflect.ListenerMethod;
import io.github.eventiful.plugin.reflect.ListenerReflector;
import io.github.eventiful.plugin.registration.EventRegistration;
import io.github.eventiful.plugin.registration.ListenerMethodRegistration;
import io.github.eventiful.plugin.registration.RegisteredListenerRegistration;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;

@AllArgsConstructor
class ListenerRegistry {
    private final Map<Listener, List<EventToken>> tokens = new HashMap<>();
    private final Map<Plugin, Set<Reference<Listener>>> listeners = new HashMap<>();
    private final ListenerReflector reflector;
    private final ServerEventBus eventBus;

    public void register(final RegisteredListener listener, final Class<? extends Event> type) {
        associateListener(listener.getListener(), listener.getPlugin());
        associateRegistration(new RegisteredListenerRegistration<>(type, listener), listener.getListener());
    }

    public void register(final Listener listener, final Plugin plugin) {
        associateListener(listener, plugin);

        for (final ListenerMethod<?> method : reflector.getMethods(listener))
            associateRegistration(new ListenerMethodRegistration<>(method, plugin), listener);
    }

    private void associateListener(final Listener listener, final Plugin plugin) {
        final Set<Reference<Listener>> ownedListeners = listeners.computeIfAbsent(plugin, k -> new HashSet<>());
        ownedListeners.add(new WeakReference<>(listener));
    }

    private void associateRegistration(final EventRegistration<?> registration, final Listener listener) {
        final List<EventToken> ownedTokens = tokens.computeIfAbsent(listener, k -> new LinkedList<>());
        ownedTokens.add(eventBus.register(registration));
    }

    public void unregister(final Listener listener) {
        final List<EventToken> ownedTokens = tokens.get(listener);

        if (ownedTokens != null)
            for (final EventToken token : ownedTokens)
                eventBus.unregister(token);

        tokens.remove(listener);
    }

    public void unregister(final Plugin plugin) {
        final Set<Reference<Listener>> references = listeners.get(plugin);

        if (references != null) {
            for (final Reference<Listener> reference : references) {
                final Listener listener = reference.get();
                if (listener != null) unregister(listener);
            }
        }

        listeners.remove(plugin);
    }
}
