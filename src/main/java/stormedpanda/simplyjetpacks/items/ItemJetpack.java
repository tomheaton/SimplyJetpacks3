package stormedpanda.simplyjetpacks.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class ItemJetpack extends ArmorItem {

    private static final JetpackMaterial JETPACK_MATERIAL = new JetpackMaterial();

    public ItemJetpack(Properties properties) {
        super(JETPACK_MATERIAL, EquipmentSlotType.CHEST, properties.rarity(Rarity.RARE).maxStackSize(1));
    }

    public static class JetpackMaterial implements IArmorMaterial {

        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return 0;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return null;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float func_230304_f_() {
            return 0;
        }
    }
}
