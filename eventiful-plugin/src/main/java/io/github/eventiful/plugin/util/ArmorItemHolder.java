package io.github.eventiful.plugin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class ArmorItemHolder {
    private final EquipmentSlot slot;
    @Setter
    private ItemStack item;
}
