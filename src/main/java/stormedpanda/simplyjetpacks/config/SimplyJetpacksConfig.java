package stormedpanda.simplyjetpacks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

@Mod.EventBusSubscriber(modid = SimplyJetpacks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplyJetpacksConfig {

    public static boolean enableStateMessages = Defaults.enableStateMessages;

    public static class Common {

        public final IntValue crafting_width;
        public final IntValue crafting_height;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Simply Jetpacks 3 - Mod Configurations")
                    .push("simplyjetpacks");

            crafting_width = builder
                    .comment("This sets the crafting width of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("simplyjetpacks.configgui.crafting_width")
                    .worldRestart()
                    .defineInRange("crafting_width", 3, 3, 5);

            crafting_height = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("simplyjetpacks.configgui.crafting_height")
                    .worldRestart()
                    .defineInRange("crafting_height", 3, 3, 5);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading event) {

    }
}