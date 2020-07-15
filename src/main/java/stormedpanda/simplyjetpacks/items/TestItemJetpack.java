package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.IEnergyStorage;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class TestItemJetpack extends ArmorItem implements IEnergyStorage {

    public static final int MAX_POWER = 10000;
    public static final int RF_PER_TICK = 10;

    @SuppressWarnings("rawtypes")
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private String armorTexture;

    @SuppressWarnings("rawtypes")
    public TestItemJetpack(IArmorMaterial material, EquipmentSlotType slot, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(material, slot, properties);
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type){
        return armorTexture;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default){
        return (A) armorApplier.apply(_default, armorSlot);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}