package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import stormedpanda.simplyjetpacks.SyncHandler;
import stormedpanda.simplyjetpacks.capability.CapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.capability.EnergyConversionStorage;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.SJStringHelper;
import stormedpanda.simplyjetpacks.util.TextUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class ItemJetpack extends ArmorItem implements IHUDInfoProvider, IEnergyContainerItem { //IEnergyStorage {

    public static final String TAG_ENERGY = "Energy";
    public static final String TAG_ENGINE = "Engine";
    public static final String TAG_HOVER = "Hover";
    public static final String TAG_E_HOVER = "EmergencyHover";

    protected int capacity = 10000;
    protected int maxReceive = 100;
    protected int maxExtract = 100;

    public String name;
    @SuppressWarnings("rawtypes")
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private String armorTexture;

    @SuppressWarnings("rawtypes")
    public ItemJetpack(String name, IArmorMaterial material, EquipmentSlotType slot, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
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

/*    public void toggleState(boolean on, ItemStack stack, String type, String tag, PlayerEntity player, boolean showState) {
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
    }*/

    public String getBaseName(ItemStack stack) {
        return this.name;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return (getBaseName(stack) == "jetpack_creative" || getBaseName(stack) == "jetpack_creative_armored" || stack.isEnchanted());
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
/*        if (NBTHelper.getBoolean(stack, TAG_ENGINE) && NBTHelper.getInt(stack, TAG_ENERGY) > 0) {
            useEnergy(stack);
        }*/

        if (NBTHelper.getBoolean(stack, TAG_ENGINE)) {
            flyUser(player, stack, this);
        }
    }

    // TESTING
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

        information(stack, this, tooltip);
        if (KeyboardUtil.isHoldingShift()) {
            shiftInformation(stack, tooltip);
        } else {
            tooltip.add(new StringTextComponent("Hold Shift for Details"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void information(ItemStack stack, ItemJetpack item, List tooltip) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(TextUtil.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }

    @OnlyIn(Dist.CLIENT)
    public void shiftInformation(ItemStack stack, List tooltip) {
        tooltip.add(new StringTextComponent("Engine: " + (NBTHelper.getBoolean(stack, TAG_ENGINE) ? "on" : "off")));
        tooltip.add(new StringTextComponent("Hover: " + (NBTHelper.getBoolean(stack, TAG_HOVER) ? "on" : "off")));
        tooltip.add(new StringTextComponent("Emergency Hover: " + (NBTHelper.getBoolean(stack, TAG_E_HOVER) ? "on" : "off")));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            ItemStack full = new ItemStack(this);
            full.getOrCreateTag().putInt(TAG_ENERGY, capacity);
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
        //return MathHelper.hsvToRGB((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
        return 0x23e232;
    }

    @Override
    public void addHUDInfo(List<ITextComponent> list, ItemStack stack) {
        list.add(new StringTextComponent("Energy: " + getEnergyStored(stack) + " FE"));
        list.add(new StringTextComponent("Engine: " + isEngineOn(stack)));
        list.add(new StringTextComponent("Hover: " + isHoverOn(stack)));
        list.add(new StringTextComponent("Emergency Hover: " + isEHoverOn(stack)));
    }

    public boolean isEngineOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_ENGINE);
    }

    public boolean toggleEngine(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_ENGINE);
        NBTHelper.flipBoolean(stack, TAG_ENGINE);
        ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.on", stateText);
        player.sendStatusMessage(msg, true);
        return !current;
    }

    public boolean isHoverOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_HOVER);
    }

    public boolean toggleHover(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_HOVER);
        NBTHelper.flipBoolean(stack, TAG_HOVER);
        ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", stateText);
        player.sendStatusMessage(msg, true);
        return !current;
    }

    public boolean isEHoverOn(ItemStack stack) { return NBTHelper.getBoolean(stack, TAG_E_HOVER); }

    public boolean toggleEHover(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_E_HOVER);
        NBTHelper.flipBoolean(stack, TAG_E_HOVER);
        ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", stateText);
        player.sendStatusMessage(msg, true);
        return !current;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    private void fly2(PlayerEntity player, double y) {
        Vector3d motion = player.getMotion();
        player.setMotion(motion.getX(), y, motion.getZ());
    }

    public void flyUser(PlayerEntity player, ItemStack stack, ItemJetpack item) {
        if (getEnergyStored(stack) > 0) {
            boolean hoverMode = isHoverOn(stack); //jetpack.isHoverModeOn(stack);
            //double hoverSpeed = 2; //Config.invertHoverSneakingBehavior == SyncHandler.isDescendKeyDown(player) ? Jetpack.values()[i].speedVerticalHoverSlow : Jetpack.values()[i].speedVerticalHover;
            //double hoverSpeed = Config.invertHoverSneakingBehavior == SyncHandler.isDescendKeyDown(user) ? Jetpack.values()[i].speedVerticalHoverSlow : Jetpack.values()[i].speedVerticalHover;
            double hoverSpeed = SimplyJetpacksConfig.invertHoverSneakingBehavior == SyncHandler.isDescendKeyDown(player) ? 0.0D : 0.45D;
            //double hoverSpeed = SyncHandler.isDescendKeyDown(player) ? 0.0D : 0.45D;
            boolean flyKeyDown = SyncHandler.isFlyKeyDown(player); // || force;
            boolean descendKeyDown = SyncHandler.isDescendKeyDown(player);
            double currentAccel = 0.15D * (player.getMotion().getY() < 0.3D ? 2.5D : 1.0D);
            double currentSpeedVertical = 0.9D * (player.isInWater() ? 0.4D : 1.0D);
            double speedVerticalHover = 0.45D;
            double speedVerticalHoverSlow = 0.0D;

            if ((flyKeyDown || hoverMode && !player.func_233570_aj_())) {
                useEnergy(stack);
                if (flyKeyDown) {
                    if (!hoverMode) {
                        //player.motionY = Math.min(player.motionY + currentAccel, currentSpeedVertical);
                        fly2(player, Math.min(player.getMotion().getY() + currentAccel, currentSpeedVertical));
                    } else {
                        if (descendKeyDown) {
                            //player.motionY = Math.min(player.motionY + currentAccel, -Jetpack.values()[i].speedVerticalHoverSlow);
                            fly2(player, Math.min(player.getMotion().getY() + currentAccel, -speedVerticalHoverSlow));
                        } else {
                            //player.motionY = Math.min(player.motionY + currentAccel, Jetpack.values()[i].speedVerticalHover);
                            fly2(player, Math.min(player.getMotion().getY() + currentAccel, speedVerticalHover));
                        }
                    }
                } else {
                    //player.motionY = Math.min(player.motionY + currentAccel, -hoverSpeed);
                    fly2(player, Math.min(player.getMotion().getY() + currentAccel, -hoverSpeed));
                }

                double baseSpeedSideways = 0.21D;
                double baseSpeedForward = 2.5D;

                float speedSideways = (float) (player.isSneaking() ? baseSpeedSideways * 0.5F : baseSpeedSideways);
                float speedForward = (float) (player.isSprinting() ? speedSideways * baseSpeedForward : speedSideways);

                if (SyncHandler.isForwardKeyDown(player)) {
                    //SimplyJetpacks.LOGGER.info("Forward Key Down");
                    player.moveRelative(1, new Vector3d(0, 0, speedForward));
                }
                if (SyncHandler.isBackwardKeyDown(player)) {
                    //SimplyJetpacks.LOGGER.info("Backward Key Down");
                    player.moveRelative(1, new Vector3d(0, 0, -speedSideways * 0.8F));
                }
                if (SyncHandler.isLeftKeyDown(player)) {
                    //SimplyJetpacks.LOGGER.info("Left Key Down");
                    player.moveRelative(1, new Vector3d(speedSideways, 0, 0));
                }
                if (SyncHandler.isRightKeyDown(player)) {
                    //SimplyJetpacks.LOGGER.info("Right Key Down");
                    player.moveRelative(1, new Vector3d(-speedSideways, 0, 0));
                }

                // TODO: find out why this doesn't work
/*                if (!player.world.isRemote()) {
                    player.fallDistance = 0.0F;
                    if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) player).connection.getNetworkManager(). floatingTickCount = 0;
                    }
                }*/
            }
        }
    }

    private void fly(PlayerEntity player, double x, double y, double z) {
        Vector3d motion = player.getMotion();
        player.setMotion(motion.getX(), y, motion.getZ());
    }

    /*public void flyUser(PlayerEntity user, ItemStack stack, ItemJetpack item, boolean force) {
        int i = MathHelper.clamp(stack.getDamage(), 0, numItems - 1);
        Item chestItem = StackUtil.getItem(stack);
        ItemJetpack jetpack = (ItemJetpack) chestItem;
        if (jetpack.isOn(stack)) {
            boolean hoverMode = jetpack.isHoverModeOn(stack);
            double hoverSpeed = Config.invertHoverSneakingBehavior == SyncHandler.isDescendKeyDown(user) ? Jetpack.values()[i].speedVerticalHoverSlow : Jetpack.values()[i].speedVerticalHover;
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
                            user.motionY = Math.min(user.getMotion().getY() + currentAccel, currentSpeedVertical);
                        } else {
                            if (descendKeyDown) {
                                user.getMotion().getY() = Math.min(user.getMotion().getY() + currentAccel, -Jetpack.values()[i].speedVerticalHoverSlow);
                            } else {
                                user.getMotion().getY() = Math.min(user.getMotion().getY() + currentAccel, Jetpack.values()[i].speedVerticalHover);
                            }
                        }
                    } else {
                        user.getMotion().getY() = Math.min(user.getMotion().getY() + currentAccel, -hoverSpeed);
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

                    if (!user.world.isRemote) {
                        user.fallDistance = 0.0F;

                        if (user instanceof EntityPlayerMP) {
                            try {
                                floatingTickCount.setInt(((EntityPlayerMP) user).connection, 0);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }*/
}
