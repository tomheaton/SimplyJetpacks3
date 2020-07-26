package stormedpanda.simplyjetpacks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

@Mod.EventBusSubscriber(modid = SimplyJetpacks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplyJetpacksConfig {

    public static boolean enableIntegrationVanilla = Defaults.enableIntegrationVanilla;
    public static boolean enableIntegrationImmersiveEngineering = Defaults.enableIntegrationImmersiveEngineering;
    public static boolean enableIntegrationMekanism = Defaults.enableIntegrationMekanism;
    public static boolean enableIntegrationEnderIO = Defaults.enableIntegrationEnderIO;
    public static boolean enableIntegrationThermalExpansion = Defaults.enableIntegrationThermalExpansion;
    public static boolean enableIntegrationThermalDynamics = Defaults.enableIntegrationThermalDynamics;

    public static boolean invertHoverSneakingBehavior = Defaults.invertHoverSneakingBehavior;

    public static boolean enableStateMessages = Defaults.enableStateMessages;

    public static class Common {

        public final BooleanValue enableIntegrationVanilla;
        public final BooleanValue enableIntegrationImmersiveEngineering;
        public final BooleanValue enableIntegrationMekanism;
        public final BooleanValue enableIntegrationEnderIO;
        public final BooleanValue enableIntegrationThermalExpansion;
        public final BooleanValue enableIntegrationThermalDynamics;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Simply Jetpacks 3 - Common Configurations").push("simplyjetpacks-common");

            builder.comment("Simply Jetpacks 3 - Client Configurations").push("integration");
            enableIntegrationVanilla = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationVanilla")
                    .worldRestart()
                    .define("enableIntegrationVanilla", Defaults.enableIntegrationVanilla);

            enableIntegrationImmersiveEngineering = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationMekanism")
                    .worldRestart()
                    .define("enableIntegrationMekanism", Defaults.enableIntegrationImmersiveEngineering);

            enableIntegrationMekanism = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationMekanism")
                    .worldRestart()
                    .define("enableIntegrationMekanism", Defaults.enableIntegrationMekanism);

            enableIntegrationEnderIO = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationEnderIO")
                    .worldRestart()
                    .define("enableIntegrationEnderIO", Defaults.enableIntegrationEnderIO);

            enableIntegrationThermalExpansion = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationThermalExpansion")
                    .worldRestart()
                    .define("enableIntegrationThermalExpansion", Defaults.enableIntegrationThermalExpansion);

            enableIntegrationThermalDynamics = builder
                    .comment("This sets the crafting height of the game. If a value is higher declared by a different mod, this becomes obsolete.")
                    .translation("config.simplyjetpacks.enableIntegrationThermalDynamics")
                    .worldRestart()
                    .define("enableIntegrationThermalDynamics", Defaults.enableIntegrationThermalDynamics);

            builder.comment("Simply Jetpacks 3 - Client Configurations").push("tuning");

            builder.pop();
        }
    }

    public static class Client {

        public final BooleanValue invertHoverSneakingBehavior;
        public final BooleanValue enableStateMessages;
        //public final String hudTextColor;
        //public final EnumValue hudTextPosition;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Simply Jetpacks 3 - Client Configurations").push("simplyjetpacks-client");

            invertHoverSneakingBehavior = builder
                    .comment("This sets whether you must sneak to hover")
                    .translation("config.simplyjetpacks.invertHoverSneakingBehavior")
                    .worldRestart()
                    .define("invertHoverSneakingBehavior", Defaults.invertHoverSneakingBehavior);

            enableStateMessages = builder
                    .comment("This sets whether or not state messages will show.")
                    .translation("config.simplyjetpacks.enableStateMessages")
                    .worldRestart()
                    .define("enableStateMessages", Defaults.enableStateMessages);


/*            hudTextColor = builder
                    .comment("This sets the color of the Jetpack HUD.")
                    .translation("config.simplyjetpacks.hudTextColor")
                    .worldRestart()
                    .define("hudTextColor", Defaults.hudTextColor);

            hudTextPosition = builder
                    .comment("This sets the position of the Jetpack HUD.")
                    .translation("config.simplyjetpacks.hudTextPosition")
                    .worldRestart()
                    .define("hudTextPosition", Defaults.hudTextPosition);*/

            builder.pop();
        }
    }

    public static class Server {

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Simply Jetpacks 3 - Server Configurations").push("simplyjetpacks-server");
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

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading event) {

    }
}