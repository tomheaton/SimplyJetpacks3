package stormedpanda.simplyjetpacks.integration;

import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;

public class IntegrationList {
    public static boolean integrateVanilla = SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get();
    public static boolean integrateImmersiveEngineering = ModType.IMMERSIVE_ENGINEERING.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get();
    public static boolean integrateMekanism = ModType.MEKANISM.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get();
    public static boolean integrateEnderIO = ModType.ENDER_IO.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationEnderIO.get();
    public static boolean integrateThermalExpansion = ModType.THERMAL_EXPANSION.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalExpansion.get();
    public static boolean integrateThermalDynamics = ModType.THERMAL_DYNAMICS.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalDynamics.get();
}
