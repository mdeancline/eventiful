
package io.github.eventiful.plugin.hook;

import io.github.eventiful.plugin.EventLogger;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.List;

@AllArgsConstructor
public class PluginHookPool {
    private final List<PluginHook> hooks = new ObjectArrayList<>();
    private final EventLogger logger;

    public void setup() {
        final PluginManager manager = Bukkit.getServer().getPluginManager();

        for (final PluginHook hook : hooks) {
            if (manager.getPlugin(hook.getName()) != null) {
                logger.logInfo("Setting up " + hook.getName() + " hook");
                hook.setup();
            }
        }
    }

    public void register(final PluginHook pluginHook) {
        hooks.add(pluginHook);
    }
}
