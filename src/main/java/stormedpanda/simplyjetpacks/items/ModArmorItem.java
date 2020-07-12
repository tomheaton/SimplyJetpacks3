package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class ModArmorItem extends ArmorItem {

    private static final ModArmorMaterial MOD_ARMOR_MATERIAL = new ModArmorMaterial();

    public ModArmorItem(EquipmentSlotType slot, Properties builder, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(MOD_ARMOR_MATERIAL, slot, builder);
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }

    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private String armorTexture;

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type){
        return armorTexture;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default){
        return (A) armorApplier.apply(_default, armorSlot);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        return true;
    }

    public static class ModArmorMaterial implements IArmorMaterial {

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
