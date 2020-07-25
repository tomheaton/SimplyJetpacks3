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
import stormedpanda.simplyjetpacks.SyncHandler;
import stormedpanda.simplyjetpacks.capability.CapabilityProviderEnergy;
import stormedpanda.simplyjetpacks.capability.EnergyConversionStorage;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.client.model.JetpackModel;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.util.KeyboardUtil;
import stormedpanda.simplyjetpacks.util.NBTHelper;
import stormedpanda.simplyjetpacks.util.Styles;
import stormedpanda.simplyjetpacks.util.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class JetpackItem extends ArmorItem implements IHUDInfoProvider, IEnergyContainerItem { //IEnergyStorage {

    public static final String TAG_ENERGY = "Energy";
    public static final String TAG_ENGINE = "Engine";
    public static final String TAG_HOVER = "Hover";
    public static final String TAG_E_HOVER = "EmergencyHover";

    private final int capacity;// = 10000;
    private final int maxReceive;// = 100;
    private final int maxExtract;// = 100;

    private final JetpackType type;

    public String name;
    //private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier;
    private BipedModel armorApplier;
    private String armorTexture;
    public final int tier;

/*    @SuppressWarnings("rawtypes")
    public ItemJetpack(String name, IArmorMaterial material, EquipmentSlotType slot, Properties properties, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture) {
        super(material, slot, properties);
        this.name = name;
        this.armorApplier = armorApplier;
        this.armorTexture = armorTexture.toString();
    }*/

    public JetpackItem(String name, JetpackType type) {
        //super(material, EquipmentSlotType.CHEST, properties);
        super(type.getArmorMaterial(), EquipmentSlotType.CHEST, type.getProperties());
        this.name = name;
        this.tier = type.getTier();
        //this.armorApplier = type.getArmorApplier();
        this.armorTexture = type.getArmorTexture();//armorTexture.toString();
        this.type = type;
        this.capacity = type.getCapacity();
        this.maxReceive = type.getMaxReceive();
        this.maxExtract = type.getMaxExtract();
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        //return type == null ? SimplyJetpacks.MODID + ":textures/armor/jetpack.png" : SimplyJetpacks.MODID + ":textures/armor/jetpack_overlay.png";
        return armorTexture;
    }

/*    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) armorApplier.apply(_default, armorSlot);
    }*/
    @OnlyIn(Dist.CLIENT)
    @Override
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        //return new JetpackModel(this);
        return new JetpackModel().applyData(_default);
    }

    public String getBaseName() { return this.name; }

    public JetpackType getType() { return type; }
    @Override
    public boolean hasEffect(ItemStack stack) {
        return (getBaseName().contains("creative") || stack.isEnchanted());
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        flyUser(player, stack, this);
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
            //pass
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

    //@Override
    public boolean canExtract() {
        return true;
    }
    //@Override
    public boolean canReceive() {
        return true;
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

    // TODO: make this check if fuel is used
    @OnlyIn(Dist.CLIENT)
    public void information(ItemStack stack, JetpackItem item, List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.tier", tier));

        if (getBaseName().contains("creative")) {
            tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.infiniteEnergy"));
        } else {
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> tooltip.add(TextUtil.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void shiftInformation(ItemStack stack, List<ITextComponent> tooltip) {
        ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
        ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);

        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.engineMode", (NBTHelper.getBoolean(stack, TAG_ENGINE) ? on : off)));
        tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.hoverMode", (NBTHelper.getBoolean(stack, TAG_HOVER) ? on : off)));
        if (type.canEHover()) {
            tooltip.add(new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (NBTHelper.getBoolean(stack, TAG_E_HOVER) ? on : off)));
        }
        if (!getBaseName().contains("creative")) {
            tooltip.add(new TranslationTextComponent("tooltip.simplyjetpacks.itemJetpack.fuelUsage", type.getFuelUsage()));
        }
    }

    // TODO: get the energy-full variants to show up in NEI
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (this.isInGroup(group) && !this.name.contains("creative")) {
            //items.add(new ItemStack(this));
            ItemStack full = new ItemStack(this);
            full.getOrCreateTag().putInt(TAG_ENERGY, capacity);
            items.add(full);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        if (!getBaseName().contains("creative")) {
            return true;
        } else { return false; }
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
        //if (type.canEHover()) { list.add(new StringTextComponent("Emergency Hover: " + isEHoverOn(stack))); }
        // TESTING
        list.add(new StringTextComponent("Emergency Hover: " + (type.canEHover() ? isEHoverOn(stack) : "NOT AVAILABLE")));
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
        if (type.canEHover()) {
            boolean current = NBTHelper.getBoolean(stack, TAG_E_HOVER);
            NBTHelper.flipBoolean(stack, TAG_E_HOVER);
            //ITextComponent stateText = SJStringHelper.localizeNew(current ? "disabled" : "enabled");
            ITextComponent on = new TranslationTextComponent("chat.simplyjetpacks.enabled").func_230530_a_(Styles.GREEN);
            ITextComponent off = new TranslationTextComponent("chat.simplyjetpacks.disabled").func_230530_a_(Styles.RED);
            ITextComponent msg = new TranslationTextComponent("chat.simplyjetpacks.itemJetpack.emergencyHoverMode", (!current ? on : off));
            player.sendStatusMessage(msg, true);
        }
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

    public void flyUser(PlayerEntity player, ItemStack stack, JetpackItem item) {
        if (isEngineOn(stack)) {
            boolean hoverMode = isHoverOn(stack);
            double hoverSpeed = SimplyJetpacksConfig.invertHoverSneakingBehavior == SyncHandler.isHoldingDown(player) ? type.getSpeedVerticalHoverSlow() : type.getSpeedVerticalHover();
            boolean flyKeyDown = SyncHandler.isHoldingUp(player); // || force;
            boolean descendKeyDown = SyncHandler.isHoldingDown(player);
            double currentAccel = type.getAccelVertical() * (player.getMotion().getY() < 0.3D ? 2.5D : 1.0D);
            double currentSpeedVertical = type.getSpeedVertical() * (player.isInWater() ? 0.4D : 1.0D);
            double speedVerticalHover = type.getSpeedVerticalHover();
            double speedVerticalHoverSlow = type.getSpeedVerticalHoverSlow();

            if ((flyKeyDown || hoverMode && !player.func_233570_aj_())) {
                // TODO: replace this with an if uses fuel check
                if (!getBaseName().contains("creative")) {
                    // TODO: add fuel usage to this
                    //item.useFuel(stack, (int) (user.isSprinting() ? Math.round(this.getFuelUsage(stack) * Jetpack.values()[i].sprintFuelModifier) : this.getFuelUsage(stack)), false);
                    int amount = (int) (player.isSprinting() ? Math.round(getFuelUsage() * type.getSprintFuelModifier()) : getFuelUsage());
                    useEnergy(stack, amount);
                }
                if (getEnergyStored(stack) > 0 || getBaseName().contains("creative")) {
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