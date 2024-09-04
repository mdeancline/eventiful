package io.github.eventiful.plugin.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class MinecraftVersions {
    public String getCraftBukkitVersion() {
        final String[] packageNameParts = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        return (packageNameParts.length >= 4) ? packageNameParts[3] : null;
    }
}
