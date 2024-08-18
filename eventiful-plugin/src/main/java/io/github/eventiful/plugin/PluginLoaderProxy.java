package io.github.eventiful.plugin;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
class PluginLoaderProxy implements PluginLoader {
    @Delegate
    private final PluginLoader source;
    private final ListenerRegistry registry;

    @NotNull
    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull final Listener listener, @NotNull final Plugin plugin) {
        registry.register(listener, plugin);

        // The Bukkit event system uses this when registering listeners in its own way
        return Collections.emptyMap();
    }
}
