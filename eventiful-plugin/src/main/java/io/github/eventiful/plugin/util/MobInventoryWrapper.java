package io.github.eventiful.plugin.util;

import io.github.eventiful.api.event.entity.ArmorEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class MobInventoryWrapper implements InventoryWrapper {
    private static final Object2IntMap<EquipmentSlot> SLOT_INDEXES = new Object2IntOpenHashMap<>(ArmorEvent.Type.values().length);
    private static final Int2ObjectMap<EquipmentSlot> SLOT_INDEXES_INVERTED = new Int2ObjectOpenHashMap<>(ArmorEvent.Type.values().length);

    private final EntityEquipment equipment;

    static {
        SLOT_INDEXES.put(EquipmentSlot.HEAD, 103);
        SLOT_INDEXES.put(EquipmentSlot.CHEST, 102);
        SLOT_INDEXES.put(EquipmentSlot.LEGS, 101);
        SLOT_INDEXES.put(EquipmentSlot.FEET, 100);

        SLOT_INDEXES_INVERTED.put(103, EquipmentSlot.HEAD);
        SLOT_INDEXES_INVERTED.put(102, EquipmentSlot.CHEST);
        SLOT_INDEXES_INVERTED.put(101, EquipmentSlot.LEGS);
        SLOT_INDEXES_INVERTED.put(100, EquipmentSlot.FEET);
    }

    @Override
    public void setItem(final int slot, final ItemStack itemStack) {
        if (!SLOT_INDEXES.containsValue(slot))
            throw new IllegalArgumentException("Invalid mob inventory slot");

        setItem(SLOT_INDEXES_INVERTED.get(slot), itemStack);
    }

    @Override
    public void setItem(final EquipmentSlot slot, final ItemStack itemStack) {
        equipment.setItem(slot, itemStack);
    }

    @Override
    public int getIndex(final EquipmentSlot slot) {
        return SLOT_INDEXES.getInt(slot);
    }

    @Override
    public void equipArmor(final ArmorItemHolder holder) {
        setItem(getIndex(holder.getSlot()), holder.getItem());
    }

    @Override
    public ItemStack getItem(final int slot) {
        if (!SLOT_INDEXES.containsValue(slot))
            throw new IllegalArgumentException("Invalid mob inventory slot");

        return equipment.getItem(SLOT_INDEXES_INVERTED.get(slot));
    }
}
