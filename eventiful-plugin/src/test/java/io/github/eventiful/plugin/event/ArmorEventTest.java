package io.github.eventiful.plugin.event;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import be.seeseemelk.mockbukkit.inventory.ItemStackMock;
import io.github.eventiful.MockEventListener;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.entity.ArmorDamageEvent;
import io.github.eventiful.plugin.TestUtils;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import io.github.eventiful.plugin.util.ItemDamageCalculator;
import io.github.eventiful.plugin.util.ItemDamageSupport;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArmorEventTest {
    private EventBus eventBus;
    private PlayerMock mockPlayer;
    private ZombieMock mockZombie;
    private MockEventListener<ArmorDamageEvent> mockArmorDamageListener;

    @Before
    public void setUp() {
        final ServerMock mockServer = TestUtils.newServerMockWithEventBus(consumedEventBus -> eventBus = consumedEventBus);
        mockPlayer = mockServer.addPlayer(TestUtils.newRandomString());

        final WorldMock mockWorld = mockPlayer.getWorld();
        mockZombie = (ZombieMock) mockWorld.spawn(mockPlayer.getLocation().clone().add(1, 0, 0), Zombie.class);

        mockArmorDamageListener = new MockEventListener<>();

        final EquipmentSlotResolver slotResolver = new EquipmentSlotResolver();
        final ItemDamageCalculator itemDamageCalculator = ItemDamageSupport.newItemDamageCalculator();
        eventBus.register(EntityDamageEvent.class, new EntityArmorDamageListener(eventBus, slotResolver, itemDamageCalculator));
        eventBus.register(ArmorDamageEvent.class, mockArmorDamageListener);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void EventDispatch_ArmorDamageEvent_DispatchesWhenPlayerAttacksDiamondArmoredMob() {
        testMobDamageWithArmor(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);
    }

    @Test
    public void EventDispatch_ArmorDamageEvent_DispatchesWhenPlayerAttacksIronArmoredMob() {
        testMobDamageWithArmor(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS);
    }

    @Test
    public void EventDispatch_ArmorDamageEvent_DispatchesWhenPlayerAttacksLeatherArmoredMob() {
        testMobDamageWithArmor(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);
    }

    @Test
    public void EventDispatch_ArmorDamageEvent_DispatchesWhenPlayerAttacksNonArmoredMob() {
        testEntityDamageWithEventFromPlayer();
    }

    private void testMobDamageWithArmor(final Material helmet, final Material chestPlate, final Material leggings, final Material boots) {
        final EntityEquipment equipment = mockZombie.getEquipment();
        assertNotNull(equipment);

        equipment.setHelmet(new ItemStackMock(helmet));
        equipment.setChestplate(new ItemStackMock(chestPlate));
        equipment.setLeggings(new ItemStackMock(leggings));
        equipment.setBoots(new ItemStackMock(boots));

        testEntityDamageWithEventFromPlayer();
        assertEquals(4, mockArmorDamageListener.getInvocationCount());
    }

    private void testEntityDamageWithEventFromPlayer() {
        mockPlayer.simulateDamage(3, mockZombie);
    }
}
