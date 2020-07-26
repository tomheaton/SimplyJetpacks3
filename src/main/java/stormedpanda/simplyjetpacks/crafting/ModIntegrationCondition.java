package stormedpanda.simplyjetpacks.crafting;

import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.integration.IntegrationList;

public class ModIntegrationCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation("simplyjetpacks", "mod_integration");
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
            return IntegrationList.integrateVanilla;
        } else if (modid.equals("immersiveengineering")) {
            return ModList.get().isLoaded(modid) && IntegrationList.integrateImmersiveEngineering;
        } else if (modid.equals("mekanism")) {
            return ModList.get().isLoaded(modid) && IntegrationList.integrateMekanism;
        } else return false;
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
