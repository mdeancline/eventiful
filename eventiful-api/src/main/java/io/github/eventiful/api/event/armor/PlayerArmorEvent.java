package io.github.eventiful.api.event.armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerArmorEvent extends ArmorEvent<Player> {
    protected PlayerArmorEvent(@NotNull final Player who, @NotNull final EquipmentSlot slot) {
        super(who, slot);
    }
}
