package io.github.eventiful.api.event.entity.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Called when an {@link Item} impacts a {@link Entity}.
 *
 * @since 1.0.0
 */
public class ItemImpactEntityEvent extends ItemImpactEvent {
    private final Entity impactedEntity;

    /**
     * Constructs a new {@code ItemImpactEntityEvent}.
     *
     * @param item            the item that impacted the entity.
     * @param source          the source responsible for propelling the item.
     * @param impactedEntity  the entity that was impacted by the item.
     */
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
