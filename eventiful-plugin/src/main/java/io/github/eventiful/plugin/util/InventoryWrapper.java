package io.github.eventiful.plugin.util;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public interface InventoryWrapper {
    void setItem(int slot, ItemStack itemStack);

    void setItem(EquipmentSlot slot, ItemStack itemStack);

    int getIndex(EquipmentSlot slot);

    void equipArmor(ArmorItemHolder holder);

    ItemStack getItem(int slot);
}
