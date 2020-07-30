package stormedpanda.simplyjetpacks.integration;

import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;

public class IntegrationList {

    public static boolean integrateVanilla;
    public static boolean integrateImmersiveEngineering;
    public static boolean integrateMekanism;
    public static boolean integrateEnderIO;
    public static boolean integrateThermalExpansion;
    public static boolean integrateThermalDynamics;

    public static void init() {
        SimplyJetpacks.LOGGER.info("Updated Integration List.");
        integrateVanilla = ModType.SIMPLY_JETPACKS.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get();
        integrateImmersiveEngineering = ModType.IMMERSIVE_ENGINEERING.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get();
        integrateMekanism = ModType.MEKANISM.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get();
        integrateEnderIO = ModType.ENDER_IO.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationEnderIO.get();
        integrateThermalExpansion = ModType.THERMAL_EXPANSION.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalExpansion.get();
        integrateThermalDynamics = ModType.THERMAL_DYNAMICS.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalDynamics.get();
    }

/*    public static boolean integrateVanilla = SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get();
    public static boolean integrateImmersiveEngineering = ModType.IMMERSIVE_ENGINEERING.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get();
    public static boolean integrateMekanism = ModType.MEKANISM.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get();
    public static boolean integrateEnderIO = ModType.ENDER_IO.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationEnderIO.get();
    public static boolean integrateThermalExpansion = ModType.THERMAL_EXPANSION.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalExpansion.get();
    public static boolean integrateThermalDynamics = ModType.THERMAL_DYNAMICS.loaded && SimplyJetpacksConfig.COMMON.enableIntegrationThermalDynamics.get();*/
}
