package io.github.eventiful.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRegisteredListener extends RegisteredListener {
    private static final Listener EMPTY_LISTENER = new Listener() {};
    private static final EventExecutor EMPTY_EXECUTOR = (listener, event) -> {};

    public AbstractRegisteredListener(final EventPriority priority, final Plugin plugin) {
        super(EMPTY_LISTENER, EMPTY_EXECUTOR, priority, plugin, false);
    }

    @NotNull
    @Override
    public final Listener getListener() {
        return super.getListener();
    }

    @NotNull
    @Override
    public final Plugin getPlugin() {
        return super.getPlugin();
    }

    @NotNull
    @Override
    public final EventPriority getPriority() {
        return super.getPriority();
    }

    @Override
    public abstract void callEvent(@NotNull final Event event);
}
