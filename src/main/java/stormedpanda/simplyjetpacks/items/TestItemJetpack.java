package stormedpanda.simplyjetpacks.items;

import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import stormedpanda.simplyjetpacks.capability.CapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.capability.EnergyConversionStorage;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.SJStringHelper;
import stormedpanda.simplyjetpacks.util.TextUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class TestItemJetpack extends ArmorItem implements IHUDInfoProvider, IEnergyContainerItem {//IEnergyStorage {

    public static final String TAG_ON = "PackOn";
    public static final String TAG_ENERGY = "Energy";

    protected int capacity = 1000;;
    protected int maxReceive = 100;
    protected int maxExtract = 100;
    public String name;
    @SuppressWarnings("rawtypes")
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private String armorTexture;

    @SuppressWarnings("rawtypes")
    public TestItemJetpack(String name, IArmorMaterial material, EquipmentSlotType slot, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
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

/*    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //information(stack, this, tooltip);
        tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.itemJetpack"));
        if (KeyboardUtil.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Im holding shift"));
        } else {
            tooltip.add(new StringTextComponent("Press shift"));
        }
    }*/

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

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        useEnergy(stack);
    }

    public void useEnergy(ItemStack container) {
        if (container.getTag() == null || !container.getTag().contains(TAG_ENERGY)) {
            //pass
        } else {
            int stored = Math.min(container.getTag().getInt(TAG_ENERGY), getMaxEnergyStored(container));
            //int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));
            int energyExtracted = 1;
            stored -= energyExtracted;
            container.getTag().putInt(TAG_ENERGY, stored);
        }
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        if (!container.hasTag()) {
            container.setTag(new CompoundNBT());
        }
        int stored = Math.min(container.getTag().getInt(TAG_ENERGY), getMaxEnergyStored(container));
        int energyReceived = Math.min(capacity - stored, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            stored += energyReceived;
            container.getTag().putInt(TAG_ENERGY, stored);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        if (container.getTag() == null || !container.getTag().contains(TAG_ENERGY)) {
            return 0;
        }
        int stored = Math.min(container.getTag().getInt(TAG_ENERGY), getMaxEnergyStored(container));
        int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            stored -= energyExtracted;
            container.getTag().putInt(TAG_ENERGY, stored);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        if (container.getTag() == null || !container.getTag().contains(TAG_ENERGY)) {
            return 0;
        }
        return Math.min(container.getTag().getInt(TAG_ENERGY), getMaxEnergyStored(container));
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return capacity;
    }

    //@Override
    public boolean canExtract() { return true; }
    //@Override
    public boolean canReceive() { return false; }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new CapabilityProviderEnergy(new EnergyConversionStorage(this, stack));
        //return new CapabilityProviderEnergy(new ItemEnergyStorage(stack, this.jetpack.capacity));
    }

    private static float getChargeRatio(ItemStack stack) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage energyStorage = optional.orElseThrow(IllegalStateException::new);
            return (float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (CapabilityEnergy.ENERGY == null) return;

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(TextUtil.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));

            ItemStack full = new ItemStack(this);
            full.getOrCreateTag().putInt("Energy", capacity);
            items.add(full);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - getChargeRatio(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void addHUDInfo(List<ITextComponent> list, ItemStack stack) {
        Style cachedStyle = Style.EMPTY;
        IFormattableTextComponent result = null;

        IFormattableTextComponent current = null;
        current = new StringTextComponent("TESTING");

        cachedStyle = cachedStyle.setColor(Color.func_240745_a_("#0f0"));
        current.func_230530_a_(cachedStyle);
        result = current;
        list.add(result);
        list.add(new StringTextComponent("Energy: " + getEnergyStored(stack) + " FE"));
    }

    /*public void flyUser(PlayerEntity user, ItemStack stack, ItemJetpack item, boolean force) {
        int i = MathHelper.clamp(stack.getItemDamage(), 0, numItems - 1);
        Item chestItem = StackUtil.getItem(stack);
        ItemJetpack jetpack = (ItemJetpack) chestItem;
        if (jetpack.isOn(stack)) {
            boolean hoverMode = true
            double hoverSpeed = 10.00;
            boolean flyKeyDown = force || SyncHandler.isFlyKeyDown(user);
            boolean descendKeyDown = SyncHandler.isDescendKeyDown(user);
            double currentAccel = Jetpack.values()[i].accelVertical * (user.motionY < 0.3D ? 2.5D : 1.0D);
            double currentSpeedVertical = Jetpack.values()[i].speedVertical * (user.isInWater() ? 0.4D : 1.0D);

            if (flyKeyDown || hoverMode && !user.onGround) {
                if (Jetpack.values()[i].usesFuel) {
                    item.useFuel(stack, (int) (user.isSprinting() ? Math.round(this.getFuelUsage(stack) * Jetpack.values()[i].sprintFuelModifier) : this.getFuelUsage(stack)), false);
                }

                if (item.getFuelStored(stack) > 0) {
                    if (flyKeyDown) {
                        if (!hoverMode) {
                            user.motionY = Math.min(user.motionY + currentAccel, currentSpeedVertical);
                        } else {
                            if (descendKeyDown) {
                                user.motionY = Math.min(user.motionY + currentAccel, -Jetpack.values()[i].speedVerticalHoverSlow);
                            } else {
                                user.motionY = Math.min(user.motionY + currentAccel, Jetpack.values()[i].speedVerticalHover);
                            }
                        }
                    } else {
                        user.motionY = Math.min(user.motionY + currentAccel, -hoverSpeed);
                    }

                    float speedSideways = (float) (user.isSneaking() ? Jetpack.values()[i].speedSideways * 0.5F : Jetpack.values()[i].speedSideways);
                    float speedForward = (float) (user.isSprinting() ? speedSideways * Jetpack.values()[i].sprintSpeedModifier : speedSideways);
                    if (SyncHandler.isForwardKeyDown(user)) {
                        user.moveRelative(0, 0, speedForward, speedForward);
                    }
                    if (SyncHandler.isBackwardKeyDown(user)) {
                        user.moveRelative(0, 0, -speedSideways, speedSideways * 0.8F);
                    }
                    if (SyncHandler.isLeftKeyDown(user)) {
                        user.moveRelative(speedSideways, 0, 0, speedSideways);
                    }
                    if (SyncHandler.isRightKeyDown(user)) {
                        user.moveRelative(-speedSideways, 0, 0, speedSideways);
                    }
                }
            }
        }
    }*/
}
