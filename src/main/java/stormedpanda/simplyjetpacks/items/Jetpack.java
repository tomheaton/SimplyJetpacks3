package stormedpanda.simplyjetpacks.items;

import net.minecraft.util.IStringSerializable;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public enum Jetpack implements IStringSerializable {

    CREATIVE_JETPACK("jetpack_creative", 6, "jetpackCreative", false, false, 0),
    CREATIVE_JETPACK_ARMORED("jetpack_creative_armored", 6, "jetpackCreativeArmored",false, true, 0);

    public final
    @Nonnull
    String baseName;
    public final
    @Nonnull
    String unlocalisedName;
    public final int tier;
    public int fuelCapacity;
    public int fuelPerTickIn;
    public int fuelPerTickOut;
    public int armorFuelPerHit;
    public int armorReduction;
    public int fuelUsage;

    public boolean isArmored;
    public int platingMeta;

    public boolean usesFuel;
    //public EnumRarity rarity;

//    public double speedVertical;
//    public double accelVertical;
//    public double speedVerticalHover;
//    public double speedVerticalHoverSlow;
//    public double speedSideways;
//    public double sprintSpeedModifier;
//    public double sprintFuelModifier;
//    public boolean emergencyHoverMode;

    private final
    @Nonnull
    List<String> jetpacks = new ArrayList<String>();

    private Jetpack(@Nonnull String baseName, int tier, String defaultConfigKey, boolean usedFuel, boolean isArmored, int platingMeta) {
        this.baseName = baseName;
        this.tier = tier;
        //this.defaults = PackDefaults.get(defaultConfigKey);
        //this.defaultParticleType = ParticleType.DEFAULT;
        this.unlocalisedName = "item.simplyjetpacks." + baseName;
        this.jetpacks.add(baseName);
        this.usesFuel = usesFuel;
        this.isArmored = isArmored;
        this.platingMeta = platingMeta;
        //this.rarity = rarity;
        //this.setArmorModel(PackModelType.JETPACK);
    }

    @Override
    public String getString() {
        return null;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public int getTier() {
        return tier;
    }

    public int getFuelPerTickIn() {
        return fuelPerTickIn;
    }

    public int getFuelPerTickOut() {
        return fuelPerTickOut;
    }

    public int getArmorFuelPerHit() {
        return armorFuelPerHit;
    }

    public int getArmorReduction() {
        return armorReduction;
    }

    public int getFuelUsage() {
        return fuelUsage;
    }
}
