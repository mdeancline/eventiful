package io.github.eventiful.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

class PluginLoaderProxy extends AbstractPluginLoader {
    private final ListenerRegistry registry;

    PluginLoaderProxy(final PluginLoader delegate, final ListenerRegistry registry) {
        super(delegate);
        this.registry = registry;
    }

    @NotNull
    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(final Listener listener, final Plugin plugin) {
        registry.register(listener, plugin);
        return Collections.emptyMap(); // The Bukkit event system uses this when registering listeners in its own way
    }
}
