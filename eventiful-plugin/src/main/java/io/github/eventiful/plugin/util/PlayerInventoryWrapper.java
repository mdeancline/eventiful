package io.github.eventiful.plugin.util;

import io.github.eventiful.api.event.entity.ArmorEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@AllArgsConstructor
public class PlayerInventoryWrapper implements InventoryWrapper {
    private static final Object2IntMap<ArmorEvent.Type> ARMOR_SLOTS = new Object2IntOpenHashMap<>(ArmorEvent.Type.values().length);

    private final PlayerInventory inventory;

    static {
        ARMOR_SLOTS.put(ArmorEvent.Type.HELMET, 5);
        ARMOR_SLOTS.put(ArmorEvent.Type.CHEST_PLATE, 6);
        ARMOR_SLOTS.put(ArmorEvent.Type.LEGGINGS, 7);
        ARMOR_SLOTS.put(ArmorEvent.Type.BOOTS, 8);
    }

    @Override
    public void setItem(final int slot, final ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    @Override
    public void setItem(final EquipmentSlot slot, final ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    @Override
    public int getIndex(final EquipmentSlot slot) {
        return ARMOR_SLOTS.getInt(slot);
    }

    @Override
    public void equipArmor(final ArmorItemHolder holder) {
        inventory.setItem(getIndex(holder.getSlot()), holder.getItem());
    }

    @Override
    public ItemStack getItem(final int slot) {
        return inventory.getItem(slot);
    }
}
