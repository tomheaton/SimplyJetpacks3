package stormedpanda.simplyjetpacks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.items.JetpackItem;

public class EnchantmentFuelEfficiency extends Enchantment {

    public EnchantmentFuelEfficiency() {
        super(Rarity.RARE, CustomEnchantmentType.JETPACK, new EquipmentSlotType[]{ EquipmentSlotType.CHEST });
    }

    @Override
    public int getMinEnchantability(int level) {
        return 8 + (level - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 50;

    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String getName() {
        return "enchantment." + SimplyJetpacks.MODID + ".fuelEfficiency";
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof JetpackItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof JetpackItem;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
