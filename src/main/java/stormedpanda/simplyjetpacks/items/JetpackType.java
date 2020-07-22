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
    TEST("jetpack_test", 69, "jetpackIron"),
    TEST_ARMORED("jetpack_test_armored", 69, "jetpackDiamond", true, 1),
    IRON("jetpack_iron", 1, "jetpackIron"),
    IRON_ARMORED("jetpack_iron_armored", 1, "jetpackIron", true, 1),
    GOLD("jetpack_gold", 2, "jetpackGold"),
    GOLD_ARMORED("jetpack_gold_armored", 2, "jetpackGold", true, 1),
    DIAMOND("jetpack_diamond", 3, "jetpackDiamond"),
    DIAMOND_ARMORED("jetpack_diamond_armored", 3, "jetpackDiamond", true, 1),
    NETHERITE("jetpack_netherite", 4, "jetpackDiamond", true, 1),
    NETHERITE_ARMORED("jetpack_netherite_armored", 4, "jetpackDiamond", true, 1),
    CREATIVE("jetpack_creative", 6, "jetpackCreative"),
    CREATIVE_ARMORED("jetpack_creative_armored", 6, "jetpackCreative", true, 1)
    ;

    private final String name;
    private final int tier;
    private int capacity;
    private int maxReceive;
    private int maxExtract;
    private final BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> getArmorApplier;
    private final ResourceLocation armorTexture;
    private boolean isArmored;
    private int platingID;
    private final Item.Properties properties;
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

/*    JetpackType(String name, int tier, String defaultConfigKey, boolean usesFuel) {
        this(name, tier, defaultConfigKey);
        this.usesFuel = usesFuel;
    }*/

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
        //this.usesFuel = true;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
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

    public int getFuelUsage() {
        return fuelUsage;
    }
    public double getSpeedVertical() {
        return speedVertical;
    }
    public double getAccelVertical() {
        return accelVertical;
    }

    public double getSpeedVerticalHoverSlow() {
        return speedVerticalHoverSlow;
    }

    public double getSpeedVerticalHover() {
        return speedVerticalHover;
    }

    public double getSpeedSideways() {
        return speedSideways;
    }

    public double getSprintSpeedModifier() {
        return sprintSpeedModifier;
    }

    public double getSprintFuelModifier() {
        return sprintFuelModifier;
    }

    public boolean canEHover() {
        return emergencyHoverMode;
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
