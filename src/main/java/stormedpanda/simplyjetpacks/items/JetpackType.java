package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.model.ModelJetpack;
import stormedpanda.simplyjetpacks.config.DefaultJetpackConfig;
import stormedpanda.simplyjetpacks.lists.ListArmorMaterial;
import java.util.EnumSet;
import java.util.function.BiFunction;

public enum JetpackType {
    TEST("jetpack_test", 1, "jetpackIron"),
    TEST_ARMORED("jetpack_test_armored", 25, "jetpackDiamond", true, 1),
    IRON("iron_jetpack", 1, "jetpackIron"),
    GOLD("gold_jetpack", 2, "jetpackGold"),
    DIAMOND("diamond_jetpack", 3, "jetpackDiamond")
    ;

    private String name;
    private int tier;
    private int capacity;
    private int maxReceive;
    private int maxExtract;
    private BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> getArmorApplier;
    private ResourceLocation armorTexture;
    private boolean isArmored;
    private int platingID;
    private Item.Properties properties;
    private boolean usesFuel;
    private Rarity rarity;

    // Configurations:
    protected static final EnumSet<JetpackType> ALL_PACKS = EnumSet.allOf(JetpackType.class);
    public final DefaultJetpackConfig defaults;
    //public int fuelCapacity;
    //public int fuelPerTickIn;
    //public int fuelPerTickOut;
    public int armorFuelPerHit;
    public int armorReduction;
    public int fuelUsage;
    public double speedVertical;
    public double accelVertical;
    public double speedVerticalHover;
    public double speedVerticalHoverSlow;
    public double speedSideways;
    public double sprintSpeedModifier;
    public double sprintFuelModifier;
    public boolean emergencyHoverMode;

    JetpackType(String name, int tier, String defaultConfigKey, boolean isArmored) {
        this(name, tier, defaultConfigKey);
        this.isArmored = isArmored;
    }

    JetpackType(String name, int tier, String defaultConfigKey, boolean isArmored, int platingID ) {
        this(name, tier, defaultConfigKey);
        this.isArmored = isArmored;
        this.platingID = platingID;
    }

    JetpackType(String name, int tier, String defaultConfigKey) {
        this.name = name;
        this.tier = tier;
        this.getArmorApplier = new ModelJetpack()::applyData;
        this.armorTexture = new ResourceLocation(("simplyjetpacks:textures/armor/" + name + ".png"));
        this.isArmored = false;
        this.properties = new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks).maxStackSize(1);
        //this.defaults = DefaultJetpackConfig.get(name);
        this.defaults = DefaultJetpackConfig.get(defaultConfigKey);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> getArmorApplier() {
        return getArmorApplier;
    }

    public String getArmorTexture() {
        return armorTexture.toString();
    }

    public IArmorMaterial getArmorMaterial() {
        if (isArmored) {
            return ListArmorMaterial.JETPACK_ARMORED;
        } else { return ListArmorMaterial.JETPACK; }
    }

    public boolean getIsArmored() {
        return isArmored;
    }

    public Item.Properties getProperties() {
        return properties;
    }

    public int getPlatingID() {
        return platingID;
    }

    public static void loadAllConfigs() {
        for (JetpackType jetpackType : ALL_PACKS) {
            jetpackType.loadJetpackConfigurations();
            SimplyJetpacks.LOGGER.info("LOADED CONFIG FOR: " + jetpackType.getName());
        }
    }

    private void loadJetpackConfigurations() {
        this.capacity = this.defaults.fuelCapacity;
        this.maxReceive = this.defaults.fuelPerTickIn;
        this.maxExtract = this.defaults.fuelPerTickOut;
        this.armorFuelPerHit = this.defaults.armorFuelPerHit;
        this.armorReduction = this.defaults.armorReduction;
        this.fuelUsage = this.defaults.fuelUsage;
        this.speedVertical = this.defaults.speedVertical;
        this.accelVertical = this.defaults.accelVertical;
        this.speedVerticalHover = this.defaults.speedVerticalHover;
        this.speedVerticalHoverSlow = this.defaults.speedVerticalHoverSlow;
        this.speedSideways = this.defaults.speedSideways;
        this.sprintSpeedModifier = this.defaults.sprintSpeedModifier;
        this.sprintFuelModifier = this.defaults.sprintFuelModifier;
        this.emergencyHoverMode = this.defaults.emergencyHoverMode;
    }
}
