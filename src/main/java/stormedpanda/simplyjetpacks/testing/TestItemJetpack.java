package stormedpanda.simplyjetpacks.testing;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stormedpanda.simplyjetpacks.lists.ListArmorMaterial;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class TestItemJetpack extends ArmorItem {

    private String name;
    private int tier;
    private String armorTexture;
    @SuppressWarnings("rawtypes")
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;

/*    public TestItemJetpack(IArmorMaterial materialIn, EquipmentSlotType slot, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, properties);
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }*/

    public TestItemJetpack(String name, EquipmentSlotType slot, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier) {
        super(ListArmorMaterial.JETPACK, slot, new Item.Properties());
        this.name = name;
        this.armorApplier = armorApplier;
        this.armorTexture = new ResourceLocation("simplyjetpacks:textures/armor/" + name + ".png").toString();
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
}
