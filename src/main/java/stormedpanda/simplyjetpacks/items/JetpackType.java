package stormedpanda.simplyjetpacks.items;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.particle.JetpackParticleType;
import stormedpanda.simplyjetpacks.config.DefaultJetpackConfig;
import stormedpanda.simplyjetpacks.integration.IntegrationList;
import stormedpanda.simplyjetpacks.lists.ArmorMaterialList;
import stormedpanda.simplyjetpacks.util.NBTHelper;

import java.util.EnumSet;

public enum JetpackType {
    CREATIVE("jetpack_creative", 6, "jetpackCreative", JetpackParticleType.RAINBOW),
    CREATIVE_ARMORED("jetpack_creative_armored", 6, "jetpackCreative", JetpackParticleType.RAINBOW, true),

    VANILLA1("jetpack_vanilla1", 1, "jetpackIron"),
    VANILLA1_ARMORED("jetpack_vanilla1_armored", 1, "jetpackIron", true, 0),
    VANILLA2("jetpack_vanilla2", 2, "jetpackGold"),
    VANILLA2_ARMORED("jetpack_vanilla2_armored", 2, "jetpackGold", true, 1),
    VANILLA3("jetpack_vanilla3", 3, "jetpackDiamond"),
    VANILLA3_ARMORED("jetpack_vanilla3_armored", 3, "jetpackDiamond", true, 2),
    VANILLA4("jetpack_vanilla4", 4, "jetpackDiamond"),
    VANILLA4_ARMORED("jetpack_vanilla4_armored", 4, "jetpackDiamond", true, 3),

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
    private final ResourceLocation armorTexture;
    private boolean isArmored;
    private int platingID;
    private final Item.Properties properties;
    private boolean usesFuel;
    private Rarity rarity;

    protected static final EnumSet<JetpackType> JETPACK_ALL = EnumSet.allOf(JetpackType.class);
    public static final EnumSet<JetpackType> JETPACK_SJ = EnumSet.range(CREATIVE, CREATIVE_ARMORED);
    public static final EnumSet<JetpackType> JETPACK_VANILLA = EnumSet.range(VANILLA1, VANILLA4_ARMORED);
    public static final EnumSet<JetpackType> JETPACK_IE = EnumSet.range(IE1, IE3_ARMORED);
    public static final EnumSet<JetpackType> JETPACK_MEK = EnumSet.range(MEK1, MEK4_ARMORED);

    // Configurations:
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
    public boolean chargerMode;

    public JetpackParticleType particleType;

/*    JetpackType(String name, int tier, String defaultConfigKey, boolean usesFuel) {
        this(name, tier, defaultConfigKey);
        this.usesFuel = usesFuel;
    }*/

    JetpackType(String name, int tier, String defaultConfigKey, JetpackParticleType particleType, boolean isArmored) {
        this(name, tier, defaultConfigKey);
        this.particleType = particleType;
        this.isArmored = isArmored;
    }

    JetpackType(String name, int tier, String defaultConfigKey, JetpackParticleType particleType) {
        this(name, tier, defaultConfigKey);
        this.particleType = particleType;
    }

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
        this.armorTexture = new ResourceLocation(("simplyjetpacks:textures/models/armor/" + name + ".png"));
        this.isArmored = false;
        this.properties = new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks).maxStackSize(1);
        this.defaults = DefaultJetpackConfig.get(defaultConfigKey);
        //this.usesFuel = true;
        this.particleType = JetpackParticleType.DEFAULT;
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
    public boolean canCharge() {
        return chargerMode;
    }

    public String getArmorTexture() {
        return armorTexture.toString();
    }

    public IArmorMaterial getArmorMaterial() {
        return isArmored ? ArmorMaterialList.JETPACK_ARMORED : ArmorMaterialList.JETPACK;
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

    public JetpackParticleType getParticleType(ItemStack stack) {
        if (stack.getTag() != null && NBTHelper.hasKey(stack, JetpackItem.TAG_PARTICLE)) {
            int particle = NBTHelper.getInt(stack, JetpackItem.TAG_PARTICLE);//, particleType.ordinal());
            JetpackParticleType particleType = JetpackParticleType.values()[particle];
            if (particleType != null) {
                return particleType;
            }
        }
        NBTHelper.setInt(stack, JetpackItem.TAG_PARTICLE, particleType.ordinal());
        return this.particleType;
    }

    public static void loadAllConfigs() {
/*        for (JetpackType jetpackType : JETPACK_ALL) {
            jetpackType.loadJetpackConfigurations();
        }*/
        for (JetpackType jetpackType : JETPACK_SJ) {
            jetpackType.loadJetpackConfigurations();
        }
        if (IntegrationList.integrateVanilla) {
        //if (SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get()) {
            for (JetpackType jetpackType : JETPACK_VANILLA) {
                jetpackType.loadJetpackConfigurations();
            }
        }
        if (IntegrationList.integrateImmersiveEngineering) {
        //if (SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get()) {
            for (JetpackType jetpackType : JETPACK_IE) {
                jetpackType.loadJetpackConfigurations();
            }
        }
        if (IntegrationList.integrateMekanism) {
        //if (SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get()) {
            for (JetpackType jetpackType : JETPACK_MEK) {
                jetpackType.loadJetpackConfigurations();
            }
        }
    }

    protected void loadJetpackConfigurations() {
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
        this.chargerMode = this.defaults.chargerMode;
    }
}
