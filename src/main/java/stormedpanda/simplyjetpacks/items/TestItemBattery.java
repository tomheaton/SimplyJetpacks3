package stormedpanda.simplyjetpacks.items;

import net.minecraft.item.Rarity;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

public class TestItemBattery extends EnergyStoringItem {
    private static final int MAX_ENERGY = 500_000;
    private static final int MAX_TRANSFER = 500;

    public TestItemBattery() {
        super(new Properties().group(SimplyJetpacks.tabSimplyJetpacks).maxStackSize(1).rarity(Rarity.UNCOMMON), MAX_ENERGY, MAX_TRANSFER);
    }
}