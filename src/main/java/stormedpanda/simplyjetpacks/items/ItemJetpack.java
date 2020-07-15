package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.SJStringHelper;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class ItemJetpack extends ArmorItem {

    public static final String TAG_ON = "PackOn";

    public String name;
    @SuppressWarnings("rawtypes")
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private String armorTexture;

    @SuppressWarnings("rawtypes")
    public ItemJetpack(String name, IArmorMaterial material, EquipmentSlotType slot, Item.Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(material, slot, properties);
        this.name = name;
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type){
        return armorTexture;
    }
/*    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return type == null ? SimplyJetpacks.MODID + ":textures/armor/jetpack.png" : SimplyJetpacks.MODID + ":textures/armor/jetpack_overlay.png";
    }*/

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default){
        return (A) armorApplier.apply(_default, armorSlot);
    }
/*    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType slot, BipedModel _default) {
        return new ModelJetpack(this);
    }*/

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //information(stack, this, tooltip);
        tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.itemJetpack"));
        if (KeyboardUtil.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Im holding shift"));
        } else {
            tooltip.add(new StringTextComponent("Press shift"));
        }
    }

    public boolean isOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_ON, true);
    }

    public void toggleState(boolean on, ItemStack stack, String type, String tag, PlayerEntity player, boolean showState) {
        NBTHelper.setBoolean(stack, tag, !on);
        if (player != null && showState) {
            ITextComponent stateText = SJStringHelper.localizeNew(on ? "disabled" : "enabled");
            //type = type != null && !type.equals("") ? "chat." + this.name + "." + type : "chat." + this.name + ".on";
            String typeText = "itemJetpack." + type;
            if (type == null) {
                typeText = "";
                if (on) { stateText.getStyle().setColor(Color.func_240745_a_("#f00")); }
                else { stateText.getStyle().setColor(Color.func_240745_a_("#0f0")); }
            }
            player.sendStatusMessage(stateText, false);
            //ITextComponent msg = SJStringHelper.localizeNew(typeText, stateText);
            ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.on", stateText);
            player.sendStatusMessage(msg, true);
        }
    }
}