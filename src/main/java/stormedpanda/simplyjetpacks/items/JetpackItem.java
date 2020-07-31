package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import stormedpanda.simplyjetpacks.capability.CapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.capability.EnergyConversionStorage;
import stormedpanda.simplyjetpacks.capability.NewCapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.client.hud.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.client.model.JetpackModel;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.handlers.SyncHandler;
import stormedpanda.simplyjetpacks.integration.IntegrationList;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.Styles;
import stormedpanda.simplyjetpacks.util.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class JetpackItem extends ArmorItem implements IHUDInfoProvider, IEnergyContainerItem {

    public static final String TAG_ENERGY = "Energy";
    public static final String TAG_ENGINE = "Engine";
    public static final String TAG_HOVER = "Hover";
    public static final String TAG_E_HOVER = "EmergencyHover";
    public static final String TAG_CHARGER = "Charger";

    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;

    private final JetpackType type;
    public String name;
    private final String armorTexture;
    public final int tier;

    public JetpackItem(JetpackType type) {
        super(type.getArmorMaterial(), EquipmentSlotType.CHEST, type.getProperties());
        this.name = type.getName();
        this.tier = type.getTier();
        this.armorTexture = type.getArmorTexture();
        this.type = type;
        this.capacity = type.getCapacity();
        this.maxReceive = type.getMaxReceive();
        this.maxExtract = type.getMaxExtract();
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return armorTexture;
    }

    @SuppressWarnings("rawtypes")
    @OnlyIn(Dist.CLIENT)
    @Override
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        return new JetpackModel().applyData(_default);
    }

    public String getBaseName() { return name; }

    public JetpackType getType() { return type; }

    public boolean isCreative() {
        return getBaseName().contains("creative");
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return (isCreative() || stack.isEnchanted());
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        flyUser(player, stack, this);
        if (this.type.canCharge() && this.isChargerOn(stack)) {
            chargeInventory(player, stack, this);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    // TESTING
    public int getFuelUsage() {
        int usage = type.getFuelUsage();
        // TODO: add fuel efficiency enchantment effects
        return usage;
    }
    public void useEnergy(ItemStack container, int amount) {
        if (container.getTag() == null || !container.getTag().contains(TAG_ENERGY)) {
            // PASS
        } else {
            int stored = Math.min(container.getTag().getInt(TAG_ENERGY), getMaxEnergyStored(container));
            //int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));
            //int energyExtracted = 1;
            int energyExtracted = amount;
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

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new CapabilityProviderEnergy(new EnergyConversionStorage(this, stack));
        //return new NewCapabilityProviderEnergy<>(new EnergyConversionStorage(this, stack), CapabilityEnergy.ENERGY, null);
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
            tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.showDetails", new StringTextComponent("Shift").setStyle(Styles.GOLD)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void information(ItemStack stack, JetpackItem item, List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.tier", tier));
        if (isCreative()) {
            tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.infiniteEnergy"));
        } else {
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> tooltip.add(TextUtil.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void shiftInformation(ItemStack stack, List<ITextComponent> tooltip) {
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);

        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (NBTHelper.getBoolean(stack, TAG_ENGINE) ? on : off)));
        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (NBTHelper.getBoolean(stack, TAG_HOVER) ? on : off)));
        if (type.canEHover()) {
            tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (NBTHelper.getBoolean(stack, TAG_E_HOVER) ? on : off)));
        }
        if (!isCreative()) {
            tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.itemJetpack.fuelUsage", type.getFuelUsage()));
        }
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            if (isCreative()) {
                items.add(new ItemStack(this));
            }
            if (IntegrationList.integrateVanilla) {
                if (getBaseName().contains("vanilla")) {
                    items.add(new ItemStack(this));
                    ItemStack full = new ItemStack(this);
                    full.getOrCreateTag().putInt(TAG_ENERGY, capacity);
                    items.add(full);
                }
            }
            if (IntegrationList.integrateImmersiveEngineering) {
                if (getBaseName().contains("ie")) {
                    items.add(new ItemStack(this));
                    ItemStack full = new ItemStack(this);
                    full.getOrCreateTag().putInt(TAG_ENERGY, capacity);
                    items.add(full);
                }
            }
            if (IntegrationList.integrateMekanism) {
                if (getBaseName().contains("mek")) {
                    items.add(new ItemStack(this));
                    ItemStack full = new ItemStack(this);
                    full.getOrCreateTag().putInt(TAG_ENERGY, capacity);
                    items.add(full);
                }
            }
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !isCreative();
    }
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - getChargeRatio(stack);
    }
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        //return MathHelper.hsvToRGB((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
        return 0x03fc49;//0x23e232;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addHUDInfo(List<ITextComponent> list, ItemStack stack) {
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);
        ITextComponent notAvailable = new TranslationTextComponent("chat.simplyjetpacks.notAvailable").setStyle(Styles.RED);
        list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (isEngineOn(stack) ? on : off)));
        list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (isHoverOn(stack) ? on : off)));
        if (type.canEHover()) {
            list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (isEHoverOn(stack) ? on : off)));
        } else {
            list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHover", notAvailable));
        }
        if (type.canCharge()) {
            list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.chargerMode", (isChargerOn(stack) ? on : off)));
        } else {
            list.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.chargerMode", notAvailable));
        }
    }

    public boolean isEngineOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_ENGINE);
    }

    public void toggleEngine(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_ENGINE);
        NBTHelper.flipBoolean(stack, TAG_ENGINE);
        //ITextComponent stateText = StringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (!current ? on : off));
        player.sendStatusMessage(msg, true);
    }

    public boolean isHoverOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_HOVER);
    }

    public void toggleHover(ItemStack stack, PlayerEntity player) {
        boolean current = NBTHelper.getBoolean(stack, TAG_HOVER);
        NBTHelper.flipBoolean(stack, TAG_HOVER);
        //ITextComponent stateText = StringHelper.localizeNew(current ? "disabled" : "enabled");
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);
        ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (!current ? on : off));
        player.sendStatusMessage(msg, true);
    }

    public boolean isEHoverOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_E_HOVER);
    }

    public void toggleEHover(ItemStack stack, PlayerEntity player) {
        if (type.canEHover()) {
            boolean current = NBTHelper.getBoolean(stack, TAG_E_HOVER);
            NBTHelper.flipBoolean(stack, TAG_E_HOVER);
            //ITextComponent stateText = StringHelper.localizeNew(current ? "disabled" : "enabled");
            ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
            ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);
            ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (!current ? on : off));
            player.sendStatusMessage(msg, true);
        }
    }

    public void doEHover(ItemStack stack, PlayerEntity player) {
        NBTHelper.setBoolean(stack, TAG_ENGINE, true);
        NBTHelper.setBoolean(stack, TAG_HOVER, true);
        //message.getStyle().setColor(Color.func_240745_a_("#00f"));
        ITextComponent message = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverModeActivated").setStyle(Styles.RED);;
        player.sendStatusMessage(message, true);
    }

    public boolean isChargerOn(ItemStack stack) {
        return NBTHelper.getBoolean(stack, TAG_CHARGER);
    }

    public void toggleCharger(ItemStack stack, PlayerEntity player) {
        if (type.canCharge()) {
            boolean current = NBTHelper.getBoolean(stack, TAG_CHARGER);
            NBTHelper.flipBoolean(stack, TAG_CHARGER);
            //ITextComponent stateText = StringHelper.localizeNew(current ? "disabled" : "enabled");
            ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").setStyle(Styles.GREEN);
            ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").setStyle(Styles.RED);
            ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.charger", (!current ? on : off));
            player.sendStatusMessage(msg, true);
        }
    }

    public void chargeInventory(PlayerEntity player, ItemStack stack, JetpackItem jetpackItem) {
        //TODO: charge inventory items
    }


    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return true;
    }

    private void fly(PlayerEntity player, double y) {
        Vector3d motion = player.getMotion();
        player.setMotion(motion.getX(), y, motion.getZ());
    }

    public void flyUser(PlayerEntity player, ItemStack stack, JetpackItem item) {
        if (isEngineOn(stack)) {
            boolean hoverMode = isHoverOn(stack);
            double hoverSpeed = SimplyJetpacksConfig.CLIENT.invertHoverSneakingBehavior.get() == SyncHandler.isHoldingDown(player) ? type.getSpeedVerticalHoverSlow() : type.getSpeedVerticalHover();
            boolean flyKeyDown = SyncHandler.isHoldingUp(player);
            boolean descendKeyDown = SyncHandler.isHoldingDown(player);
            double currentAccel = type.getAccelVertical() * (player.getMotion().getY() < 0.3D ? 2.5D : 1.0D);
            double currentSpeedVertical = type.getSpeedVertical() * (player.isInWater() ? 0.4D : 1.0D);
            double speedVerticalHover = type.getSpeedVerticalHover();
            double speedVerticalHoverSlow = type.getSpeedVerticalHoverSlow();

            if ((flyKeyDown || hoverMode && !player.isOnGround())) {
                // TODO: replace this with an if uses fuel check
                if (!isCreative()) {
                    // TODO: add fuel usage to this
                    //item.useFuel(stack, (int) (user.isSprinting() ? Math.round(this.getFuelUsage(stack) * Jetpack.values()[i].sprintFuelModifier) : this.getFuelUsage(stack)), false);
                    int amount = (int) (player.isSprinting() ? Math.round(getFuelUsage() * type.getSprintFuelModifier()) : getFuelUsage());
                    useEnergy(stack, amount);
                }
                if (getEnergyStored(stack) > 0 || isCreative()) {
                    if (flyKeyDown) {
                        if (!hoverMode) {
                            fly(player, Math.min(player.getMotion().getY() + currentAccel, currentSpeedVertical));
                        } else {
                            if (descendKeyDown) {
                                fly(player, Math.min(player.getMotion().getY() + currentAccel, -speedVerticalHoverSlow));
                            } else {
                                fly(player, Math.min(player.getMotion().getY() + currentAccel, speedVerticalHover));
                            }
                        }
                    } else {
                        fly(player, Math.min(player.getMotion().getY() + currentAccel, -hoverSpeed));
                    }

                    double baseSpeedSideways = type.getSpeedSideways();
                    double baseSpeedForward = type.getSprintSpeedModifier();
                    float speedSideways = (float) (player.isSneaking() ? baseSpeedSideways * 0.5F : baseSpeedSideways);
                    float speedForward = (float) (player.isSprinting() ? speedSideways * baseSpeedForward : speedSideways);

                    if (SyncHandler.isHoldingForwards(player)) {
                        player.moveRelative(1, new Vector3d(0, 0, speedForward));
                    }
                    if (SyncHandler.isHoldingBackwards(player)) {
                        player.moveRelative(1, new Vector3d(0, 0, -speedSideways * 0.8F));
                    }
                    if (SyncHandler.isHoldingLeft(player)) {
                        player.moveRelative(1, new Vector3d(speedSideways, 0, 0));
                    }
                    if (SyncHandler.isHoldingRight(player)) {
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