package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stormedpanda.simplyjetpacks.gui.TestContainer;
import stormedpanda.simplyjetpacks.items.TestItemBattery;

import java.awt.geom.RectangularShape;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyJetpacks.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyJetpacks.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SimplyJetpacks.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //ScreenManager.registerFactory(RegistryHandler.TEST_CONTAINER, factory);
    // TESTING:
   //public final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("test_container", () -> IForgeContainerType.create((id, inv, data) -> new TestScreenContainer(RegistryHandler.TEST_CONTAINER, inv, new StringTextComponent("LOl"))));
    //public static final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("test_container", () -> IForgeContainerType.create((id, inv, data) -> new TestScreenContainer(id, inv, data)));
    //public static final RegistryObject<ContainerType<JetpackGuiContainer>> JETPACK_GUI = CONTAINERS.register("jetpack_gui",() -> IForgeContainerType.create((id, inv, data) -> new JetpackGuiContainer(inv)));
    //public static final RegistryObject<ContainerType<TestGuiContainer>> JETPACK_GUI_2 = CONTAINERS.register("jetpack_gui_2", () -> IForgeContainerType.create((id, inv, data) -> new TestGuiContainer(null, id, inv, inv)));
    public static final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("test_container",
            () -> IForgeContainerType.create((id, inv, data) -> new TestContainer(id, inv)));
    //public static final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("jetpack_gui_1", TestContainer::new);

    public static final RegistryObject<Item> TEST_BATTERY = ITEMS.register("battery", TestItemBattery::new);

    // THRUSTERS:
    public static final RegistryObject<Item> THRUSTER_IRON = ITEMS.register("thruster_iron", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_GOLD = ITEMS.register("thruster_gold", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_DIAMOND = ITEMS.register("thruster_diamond", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_NETHERITE = ITEMS.register("thruster_netherite", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    // MISC:
    public static final RegistryObject<Item> LEATHER_STRAP = ITEMS.register("leather_strap", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    public static final RegistryObject<Item> INGOT_DRAGON = ITEMS.register("ingot_dragon", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    public static final RegistryObject<Item> JETPACK_POTATO = ITEMS.register("jetpack_potato", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    // BLOCKS:
}
