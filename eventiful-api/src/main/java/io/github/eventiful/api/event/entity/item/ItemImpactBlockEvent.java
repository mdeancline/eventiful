package io.github.eventiful.api.event.entity.item;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when an {@link Item} impacts a {@link Block}.
 *
 * @since 1.0.0
 */
public class ItemImpactBlockEvent extends ItemImpactEvent {
    private final Block impactedBlock;
    private final BlockFace impactedFace;

    @ApiStatus.Internal
    public ItemImpactBlockEvent(final Item item, final ProjectileSource source, final Block impactedBlock, final BlockFace impactedFace) {
        super(item, source);
        this.impactedBlock = impactedBlock;
        this.impactedFace = impactedFace;
    }

    /**
     * Retrieves the block that the item has impacted.
     *
     * @return The impacted block.
     */
    public Block getImpactedBlock() {
        return impactedBlock;
    }

    /**
     * Retrieves the face of the block that was impacted by the item.
     *
     * @return The impacted block face.
     */
    public BlockFace getImpactedFace() {
        return impactedFace;
    }
}
