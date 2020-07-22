package stormedpanda.simplyjetpacks.config;

import java.util.HashMap;
import java.util.Map;

public abstract class Defaults {

    // Mod Integration
    public static final boolean enableIntegrationVanilla = true;
    public static final boolean enableIntegrationMekanism = true;
    public static final boolean enableIntegrationEnderIO = true;
    public static final boolean enableIntegrationThermalExpansion = true;
    public static final boolean enableIntegrationThermalDynamics = true;

    // Controls
    public static final boolean invertHoverSneakingBehavior = false;

    // GUI
    public static final boolean enableStateMessages = true;

    // JETPACK TUNING (TEMPORARY)
    private static final Map<String, DefaultJetpackConfig> DEFAULTS = new HashMap<String, DefaultJetpackConfig>();

    // PackBase
    public Integer fuelCapacity;
    public Integer fuelUsage;
    public Integer fuelPerTickIn;
    public Integer fuelPerTickOut;
    public Integer armorReduction;
    public Integer armorFuelPerHit;
    public Integer enchantability;

    // Jetpack
    public Double speedVertical;
    public Double accelVertical;
    public Double speedVerticalHover;
    public Double speedVerticalHoverSlow;
    public Double speedSideways;
    public Double sprintSpeedModifier;
    public Double sprintFuelModifier;
    public Boolean emergencyHoverMode;

}
