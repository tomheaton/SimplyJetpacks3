package stormedpanda.simplyjetpacks.integration;

import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;

public class IntegrationList {
    public static boolean integrateVanilla = SimplyJetpacksConfig.enableIntegrationVanilla;
    public static boolean integrateImmersiveEngineering = ModType.IMMERSIVE_ENGINEERING.loaded && SimplyJetpacksConfig.enableIntegrationImmersiveEngineering;
    public static boolean integrateMekanism = ModType.MEKANISM.loaded && SimplyJetpacksConfig.enableIntegrationMekanism;
    public static boolean integrateEnderIO = ModType.ENDER_IO.loaded && SimplyJetpacksConfig.enableIntegrationEnderIO;
    public static boolean integrateThermalExpansion = ModType.THERMAL_EXPANSION.loaded && SimplyJetpacksConfig.enableIntegrationThermalExpansion;
    public static boolean integrateThermalDynamics = ModType.THERMAL_DYNAMICS.loaded && SimplyJetpacksConfig.enableIntegrationThermalDynamics;
}
