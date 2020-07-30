package stormedpanda.simplyjetpacks.config;

import java.util.HashMap;
import java.util.Map;

public class DefaultJetpackConfig {
    
    private static final Map<String, DefaultJetpackConfig> DEFAULTS = new HashMap<>();
    //public final Section section;

    // Base
    public int fuelCapacity;
    public int fuelUsage;
    public int fuelPerTickIn;
    public int fuelPerTickOut;
    public int armorReduction;
    public int armorFuelPerHit;
    public int enchantability;

    // Jetpack
    public double speedVertical;
    public double accelVertical;
    public double speedVerticalHover;
    public double speedVerticalHoverSlow;
    public double speedSideways;
    public double sprintSpeedModifier;
    public double sprintFuelModifier;
    public boolean emergencyHoverMode;
    public boolean chargerMode;

    public DefaultJetpackConfig(String key, String sectionTitle) {
        //this.section = new Section(false, "Tuning - " + sectionTitle, "tuning." + key);
        DEFAULTS.put(key, this);
    }

    public static DefaultJetpackConfig get(String key)
    {
        return DEFAULTS.get(key);
    }

    // TODO: add other jetpack values back in
    static {
        // Simply Jetpacks
        DefaultJetpackConfig d = new DefaultJetpackConfig("jetpackPotato", "Tuberous Jetpack");
        d.fuelCapacity = 1200;
        d.fuelUsage = 45;
        d.speedVertical = 0.9D;
        d.accelVertical = 0.5D;

        d = new DefaultJetpackConfig("jetpackCreative", "Creative Jetpack");
        d.fuelCapacity = 0;
        d.fuelPerTickOut = 0;
        d.fuelPerTickIn = 0;
        d.armorReduction = 12;
        d.enchantability = 20;
        d.speedVertical = 0.9D;
        d.accelVertical = 0.15D;
        d.speedVerticalHover = 0.45D;
        d.speedVerticalHoverSlow = 0.0D;
        d.speedSideways = 0.21D;
        d.sprintSpeedModifier = 2.5D;
        d.emergencyHoverMode = true;
        d.chargerMode = true;

        //if (IntegrationList.integrateVanilla) {
        if (true) {
            d = new DefaultJetpackConfig("jetpackIron", "Iron Jetpack (Vanilla 1)");
            d.fuelCapacity = 80000;
            d.fuelUsage = 32;
            d.fuelPerTickIn = 400;
            d.armorReduction = 5;
            d.armorFuelPerHit = 80;
            d.enchantability = 4;
            d.speedVertical = 0.22D;
            d.accelVertical = 0.1D;
            d.speedVerticalHover = 0.18D;
            d.speedVerticalHoverSlow = 0.14D;
            d.speedSideways = 0.0D;
            d.sprintSpeedModifier = 1.0D;
            d.sprintFuelModifier = 1.0D;
            d.emergencyHoverMode = false;
            d.chargerMode = false;

            d = new DefaultJetpackConfig("jetpackGold", "Gold Jetpack (Vanilla 2)");
            d.fuelCapacity = 400000;
            d.fuelUsage = 50;
            d.fuelPerTickIn = 2000;
            d.armorReduction = 6;
            d.armorFuelPerHit = 80;
            d.enchantability = 8;
            d.speedVertical = 0.4D;
            d.accelVertical = 0.12D;
            d.speedVerticalHover = 0.2D;
            d.speedVerticalHoverSlow = 0.1D;
            d.speedSideways = 0.1D;
            d.sprintSpeedModifier = 1.0D;
            d.sprintFuelModifier = 1.0D;
            d.emergencyHoverMode = false;
            d.chargerMode = false;

            d = new DefaultJetpackConfig("jetpackDiamond", "Diamond Jetpack (Vanilla 3)");
            d.fuelCapacity = 20000000;
            d.fuelUsage = 450;
            d.fuelPerTickIn = 50000;
            d.armorReduction = 8;
            d.armorFuelPerHit = 160;
            d.enchantability = 17;
            d.speedVertical = 0.8D;
            d.accelVertical = 0.14D;
            d.speedVerticalHover = 0.4D;
            d.speedVerticalHoverSlow = 0.005D;
            d.speedSideways = 0.19D;
            d.sprintSpeedModifier = 1.8D;
            d.sprintFuelModifier = 4.0D;
            d.emergencyHoverMode = true;
            d.chargerMode = true;
        }
    }
}
