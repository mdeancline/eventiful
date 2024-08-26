package io.github.eventiful.plugin.player.armor;

import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorEvent;
import io.github.eventiful.api.event.player.PlayerArmorEquipEvent;
import io.github.eventiful.api.listener.CancellableEventListener;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@AllArgsConstructor
public class PlayerArmorInteractListener extends CancellableEventListener<PlayerInteractEvent> {
    private final EventBus eventBus;

    @Override
    protected void handleCancellable(final PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        final Material material = event.getMaterial();
        final ArmorEvent.Type armorType = ArmorEvent.Type.of(material);

        if (armorType != null && event.useInteractedBlock() != Event.Result.DENY) {
            final Player player = event.getPlayer();
            final PlayerArmorEquipEvent equipEvent = new PlayerArmorEquipEvent(player, armorType, PlayerArmorEquipEvent.Cause.HOTBAR);
            equipEvent.setArmorItem(event.getItem());
            eventBus.dispatch(equipEvent);
            player.getInventory().setItem(armorType.getInventorySlot(), equipEvent.getArmorItem());
        }
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.HIGHEST;
    }

    @Override
    protected boolean isIgnoringCancelled() {
        return true;
    }
}
