package stormedpanda.simplyjetpacks.enchantments;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;
import stormedpanda.simplyjetpacks.items.ItemJetpack;

import java.util.function.Predicate;

public class CustomEnchantmentType {

    public static final EnchantmentType JETPACK = addEnchantment("jetpack", ItemJetpack.class::isInstance);
    //public static final EnchantmentType JETPACK = addEnchantment("jetpack", item -> item instanceof ItemJetpack);

    private static EnchantmentType addEnchantment(String name, Predicate<Item> condition) {
        return EnchantmentType.create(name, condition);
    }
}