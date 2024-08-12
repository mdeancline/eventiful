package io.github.eventiful.plugin;

import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@AllArgsConstructor
public class HandlerListProxy extends HandlerList {
    private final Class<? extends Event> eventType;
    private final ListenerRegistry listenerRegistry;
    private final RegisteredListener[] listenerProxies;

    @Override
    public void register(@NotNull final RegisteredListener listener) {
        listenerRegistry.register(listener, eventType);
    }

    @Override
    public void registerAll(final Collection<RegisteredListener> listeners) {
        for (final RegisteredListener listener : listeners)
            register(listener);
    }

    @Override
    public void unregister(final RegisteredListener listener) {
        listenerRegistry.unregister(listener.getListener());
    }

    @Override
    public void unregister(@NotNull final Plugin plugin) {
        listenerRegistry.unregister(plugin);
    }

    @Override
    public void unregister(@NotNull final Listener listener) {
        listenerRegistry.unregister(listener);
    }

    @NotNull
    @Override
    public RegisteredListener[] getRegisteredListeners() {
        return listenerProxies;
    }
}
