package stormedpanda.simplyjetpacks;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.client.handlers.HUDHandler;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.crafting.PlatingReturnHandler;
import stormedpanda.simplyjetpacks.gui.TestContainer;
import stormedpanda.simplyjetpacks.gui.TestScreen;
import stormedpanda.simplyjetpacks.network.NetworkHandler;
import java.util.stream.Collectors;

@Mod(SimplyJetpacks.MODID)
public class SimplyJetpacks {

    public static SimplyJetpacks instance;

    public static final String MODID = "simplyjetpacks";
    public static final String MODNAME = "Simply Jetpacks 3";
    public static final String VERSION = "${version}";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeTabSimplyJetpacks tabSimplyJetpacks = new CreativeTabSimplyJetpacks();

    public SimplyJetpacks() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::CommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(KeyBindHandler.class);
        MinecraftForge.EVENT_BUS.register(new FlyHandler());
        MinecraftForge.EVENT_BUS.register(new SyncHandler());
        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        MinecraftForge.EVENT_BUS.register(new PlatingReturnHandler());

        // TODO: get both configs in one folder
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SimplyJetpacksConfig.COMMON_SPEC, "simplyjetpacks-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SimplyJetpacksConfig.CLIENT_SPEC, "simplyjetpacks-client.toml");
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SimplyJetpacksConfig.CLIENT_SPEC, "simplyjetpacks-client.toml"); // TODO: add Server Config

        RegistryHandler.init();
    }

    private void CommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setup Method registered.");
        KeyBindHandler.setup();
        NetworkHandler.registerMessages();
    }

    private void ClientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client Setup Method registered.");
        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(TestContainer.TYPE, TestScreen::new);
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("simplyjetpacks", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().map(m->m.getMessageSupplier().get()).collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Server starting...");
    }

    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        LOGGER.info("Server stopping...");
        SyncHandler.clearAll();
    }
}
