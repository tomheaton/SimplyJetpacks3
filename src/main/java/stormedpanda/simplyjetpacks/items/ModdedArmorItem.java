package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class ModdedArmorItem extends ArmorItem {

    @SuppressWarnings("rawtypes")
	public ModdedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(materialIn, slot, builder);
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }

	@SuppressWarnings("rawtypes")
	private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;

    private String armorTexture;

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type){
        return armorTexture;
    }

    // TODO: get this to only work if the item is the creative jetpack
    @Override
    public boolean hasEffect(ItemStack itemStack) {
        if(itemStack.getItem().toString() == "jetpack_creative") {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default){
        return (A) armorApplier.apply(_default, armorSlot);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("lol"));
    }
}
