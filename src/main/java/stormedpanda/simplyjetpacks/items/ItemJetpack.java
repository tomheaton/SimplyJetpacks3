package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.util.Styles;
import stormedpanda.simplyjetpacks.SyncHandler;
import stormedpanda.simplyjetpacks.capability.CapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.capability.EnergyConversionStorage;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.TextUtil;

import javax.annotation.Nonnull;
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

    private final int numItems;

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

        numItems = Jetpack.values().length;
    }

    // TESTING:
    public void getDamageNumber(ItemStack stack) {
        int i = MathHelper.clamp(stack.getDamage(), 0, numItems - 1);
        SimplyJetpacks.LOGGER.info("Details: " + i);
        SimplyJetpacks.LOGGER.info("Details: " + Jetpack.values()[i].baseName);
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return armorTexture;
    }
/*    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return type == null ? SimplyJetpacks.MODID + ":textures/armor/jetpack.png" : SimplyJetpacks.MODID + ":textures/armor/jetpack_overlay.png";
    }*/

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) armorApplier.apply(_default, armorSlot);
    }
/*    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType slot, BipedModel _default) {
        return new ModelJetpack(this);
    }*/

    public String getBaseName(ItemStack stack) {
        return this.name;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return (getBaseName(stack).equals("jetpack_creative") || getBaseName(stack).equals("jetpack_creative_armored") || stack.isEnchanted());
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        flyUser(player, stack, this);
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
    public boolean canExtract() {
        return true;
    }

    //@Override
    public boolean canReceive() {
        return false;
    }

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
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (CapabilityEnergy.ENERGY == null) return;
        information(stack, this, tooltip);
        if (KeyboardUtil.isHoldingShift()) {
            shiftInformation(stack, tooltip);
        } else {
            // TODO: make this use keybinding value rather than just hardcoded
            tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.showDetails", new StringTextComponent("Shift").func_230530_a_(Styles.GOLD)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void information(ItemStack stack, ItemJetpack item, List<ITextComponent> tooltip) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(TextUtil.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }

    @OnlyIn(Dist.CLIENT)
    public void shiftInformation(ItemStack stack, List<ITextComponent> tooltip) {
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);

        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (NBTHelper.getBoolean(stack, TAG_ENGINE) ? on : off)));
        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (NBTHelper.getBoolean(stack, TAG_HOVER) ? on : off)));
        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (NBTHelper.getBoolean(stack, TAG_E_HOVER) ? on : off)));
    }

    // TODO: get the full variants to show up in NEI
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        //super.fillItemGroup(group, items);
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

    public void toggleEngine(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_ENGINE);
        NBTHelper.flipBoolean(stack, TAG_ENGINE);
        //ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (!current ? on : off));
        player.sendStatusMessage(msg, true);
    }

    public boolean isHoverOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_HOVER);
    }

    public void toggleHover(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_HOVER);
        NBTHelper.flipBoolean(stack, TAG_HOVER);
        //ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (!current ? on : off));
        player.sendStatusMessage(msg, true);
    }

    public boolean isEHoverOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_E_HOVER);
    }

    public void toggleEHover(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_E_HOVER);
        NBTHelper.flipBoolean(stack, TAG_E_HOVER);
        //ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (!current ? on : off));
        player.sendStatusMessage(msg, true);
    }

    public void doEHover(ItemStack stack, PlayerEntity player) {
        NBTHelper.setBoolean(stack, TAG_ENGINE, true);
        NBTHelper.setBoolean(stack, TAG_HOVER, true);
        //message.getStyle().setColor(Color.func_240745_a_("#00f"));
        ITextComponent message = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverModeActivated").func_230530_a_(Styles.RED);;
        player.sendStatusMessage(message, true);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    private void fly(PlayerEntity player, double y) {
        Vector3d motion = player.getMotion();
        player.setMotion(motion.getX(), y, motion.getZ());
    }

    public void flyUser(PlayerEntity player, ItemStack stack, ItemJetpack item) {
        //if (getEnergyStored(stack) > 0) {
        if (isEngineOn(stack)) {
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
                // TODO: replace this with an if uses fuel check
                if (!getBaseName(stack).equals("jetpack_creative") || !getBaseName(stack).equals("jetpack_creative_armored")) {
                    // TODO: add fuel usage to this
                    useEnergy(stack);
                }
                if (getEnergyStored(stack) > 0) {
                    if (flyKeyDown) {
                        if (!hoverMode) {
                            //player.motionY = Math.min(player.motionY + currentAccel, currentSpeedVertical);
                            fly(player, Math.min(player.getMotion().getY() + currentAccel, currentSpeedVertical));
                        } else {
                            if (descendKeyDown) {
                                //player.motionY = Math.min(player.motionY + currentAccel, -Jetpack.values()[i].speedVerticalHoverSlow);
                                fly(player, Math.min(player.getMotion().getY() + currentAccel, -speedVerticalHoverSlow));
                            } else {
                                //player.motionY = Math.min(player.motionY + currentAccel, Jetpack.values()[i].speedVerticalHover);
                                fly(player, Math.min(player.getMotion().getY() + currentAccel, speedVerticalHover));
                            }
                        }
                    } else {
                        //player.motionY = Math.min(player.motionY + currentAccel, -hoverSpeed);
                        fly(player, Math.min(player.getMotion().getY() + currentAccel, -hoverSpeed));
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

                    if (!player.world.isRemote()) {
                        player.fallDistance = 0.0F;
                        if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).connection.floatingTickCount = 0;
                        }
                    }
                }
            }
        }

        //if (!player.world.isRemote && Jetpack.values()[i].emergencyHoverMode && this.isEHoverOn(stack)) {
        if (!player.world.isRemote && this.isEHoverOn(stack)) {
            if (item.getEnergyStored(stack) > 0 && (!this.isHoverOn(stack) || !this.isEngineOn(stack))) {
                if (player.getPositionVec().getY() < -5) {
                    this.doEHover(stack, player);
                } else {
                    if (!player.isCreative() && player.fallDistance - 1.2F >= player.getHealth()) {
                        for (int j = 0; j <= 16; j++) {
                            int x = Math.round((float) player.getPositionVec().getX() - 0.5F);
                            int y = Math.round((float) player.getPositionVec().getY()) - j;
                            int z = Math.round((float) player.getPositionVec().getZ() - 0.5F);
                            if (!player.world.isAirBlock(new BlockPos(x, y, z))) {
                                this.doEHover(stack, player);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}