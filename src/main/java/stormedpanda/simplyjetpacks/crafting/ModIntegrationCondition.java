package stormedpanda.simplyjetpacks.crafting;

import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;

public class ModIntegrationCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(SimplyJetpacks.MODID, "mod_integration");
    private final String modid;

    public ModIntegrationCondition(String modid) {
        this.modid = modid;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        if (modid.equals("vanilla")) {
            SimplyJetpacks.LOGGER.info("VANILLA: " + (SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get()));
            return SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get();
        }
        if (modid.equals("immersiveengineering")) {
            SimplyJetpacks.LOGGER.info("IE: " + (ModList.get().isLoaded(modid) && SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get()));
            return SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get();
        }
        if (modid.equals("mekanism")) {
            SimplyJetpacks.LOGGER.info("MEK: " + (ModList.get().isLoaded(modid) && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get()));
            return ModList.get().isLoaded(modid) && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get();
        }
        return false;
        /*switch (modid) {
            case "vanilla":
                return SimplyJetpacksConfig.COMMON.enableIntegrationVanilla.get();
            case "immersiveengineering":
                return ModList.get().isLoaded(modid) && SimplyJetpacksConfig.COMMON.enableIntegrationImmersiveEngineering.get();
            case "mekanism":
                return ModList.get().isLoaded(modid) && SimplyJetpacksConfig.COMMON.enableIntegrationMekanism.get();
            default:
                return false;
        }*/
    }

    @Override
    public String toString() {
        return "mod_integration(\"" + modid + "\")";
    }

    public static class Serializer implements IConditionSerializer<ModIntegrationCondition> {
        public static final ModIntegrationCondition.Serializer INSTANCE = new ModIntegrationCondition.Serializer();

        @Override
        public void write(JsonObject json, ModIntegrationCondition value) {
            json.addProperty("modid", value.modid);
        }

        @Override
        public ModIntegrationCondition read(JsonObject json) {
            return new ModIntegrationCondition(JSONUtils.getString(json, "modid"));
        }

        @Override
        public ResourceLocation getID() {
            return ModIntegrationCondition.NAME;
        }
    }
}
