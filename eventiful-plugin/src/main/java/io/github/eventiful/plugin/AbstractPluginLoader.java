package io.github.eventiful.plugin;

import lombok.experimental.Delegate;
import org.bukkit.plugin.PluginLoader;

public abstract class AbstractPluginLoader implements PluginLoader {
    @Delegate
    private final PluginLoader delegate;

    protected AbstractPluginLoader(final PluginLoader delegate) {
        this.delegate = delegate;
    }
}
