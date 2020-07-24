package stormedpanda.simplyjetpacks.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.model.JetpackModel;
import stormedpanda.simplyjetpacks.config.DefaultJetpackConfig;
import stormedpanda.simplyjetpacks.lists.ArmorMaterialList;
import java.util.EnumSet;
import java.util.function.BiFunction;

public enum JetpackType {
    IRON("jetpack_iron", 1, "jetpackIron"),
    IRON_ARMORED("jetpack_iron_armored", 1, "jetpackIron", true, 0),
    GOLD("jetpack_gold", 2, "jetpackGold"),
    GOLD_ARMORED("jetpack_gold_armored", 2, "jetpackGold", true, 1),
    DIAMOND("jetpack_diamond", 3, "jetpackDiamond"),
    DIAMOND_ARMORED("jetpack_diamond_armored", 3, "jetpackDiamond", true, 2),
    NETHERITE("jetpack_netherite", 4, "jetpackDiamond"),
    NETHERITE_ARMORED("jetpack_netherite_armored", 4, "jetpackDiamond", true, 3),
    CREATIVE("jetpack_creative", 6, "jetpackCreative"),
    CREATIVE_ARMORED("jetpack_creative_armored", 6, "jetpackCreative", true),
    TEST("jetpack_test", 69, "jetpackDiamond"),
    TEST_ARMORED("jetpack_test_armored", 69, "jetpackDiamond", true),

    IE1("jetpack_ie1", 1, "jetpackIron"),
    IE1_ARMORED("jetpack_ie1_armored", 1, "jetpackIron", true, 4),
    IE2("jetpack_ie2", 2, "jetpackGold"),
    IE2_ARMORED("jetpack_ie2_armored", 2, "jetpackGold", true, 5),
    IE3("jetpack_ie3", 3, "jetpackDiamond"),
    IE3_ARMORED("jetpack_ie3_armored", 3, "jetpackDiamond", true, 6),

    MEK1("jetpack_mek1", 1, "jetpackIron"),
    MEK1_ARMORED("jetpack_mek1_armored", 1, "jetpackIron", true, 7),
    MEK2("jetpack_mek2", 2, "jetpackGold"),
    MEK2_ARMORED("jetpack_mek2_armored", 2, "jetpackGold", true, 8),
    MEK3("jetpack_mek3", 3, "jetpackDiamond"),
    MEK3_ARMORED("jetpack_mek3_armored", 3, "jetpackDiamond", true, 9),
    MEK4("jetpack_mek4", 3, "jetpackDiamond"),
    MEK4_ARMORED("jetpack_mek4_armored", 3, "jetpackDiamond", true, 10)
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
    private Item platingItem;
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
        //this.platingItem = platingItem;
    }

    JetpackType(String name, int tier, String defaultConfigKey) {
        this.name = name;
        this.tier = tier;
        this.getArmorApplier = new JetpackModel()::applyData;
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
            return ArmorMaterialList.JETPACK_ARMORED;
        } else { return ArmorMaterialList.JETPACK; }
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

    public Item getPlatingItem() {
        return platingItem;
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
