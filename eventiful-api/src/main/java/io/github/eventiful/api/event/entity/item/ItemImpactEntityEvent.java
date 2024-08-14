package io.github.eventiful.api.event.entity.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when an {@link Item} impacts a {@link Entity}.
 *
 * @since 1.0.0
 */
public class ItemImpactEntityEvent extends ItemImpactEvent {
    private final Entity impactedEntity;

    @ApiStatus.Internal
    public ItemImpactEntityEvent(final Item item, final ProjectileSource source, final Entity impactedEntity) {
        super(item, source);
        this.impactedEntity = impactedEntity;
    }

    /**
     * Retrieves the entity that was impacted by the item.
     *
     * @return The impacted entity.
     */
    public Entity getImpactedEntity() {
        return impactedEntity;
    }
}
