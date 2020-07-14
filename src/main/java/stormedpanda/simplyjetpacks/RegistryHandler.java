package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyJetpacks.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyJetpacks.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // TESTING:
/*    public static final RegistryObject<Item> JETPACK_TEST = ITEMS.register("jetpack_test", () ->
            new TestItemJetpack(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    public static final RegistryObject<Item> JETPACK_TEST_ARMORED = ITEMS.register("jetpack_test_armored", () ->
            new TestItemJetpack(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));*/

    // JETPACKS:
/*    public static final RegistryObject<Item> JETPACK_IRON = ITEMS.register("jetpack_iron", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_GOLD = ITEMS.register("jetpack_gold", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_DIAMOND = ITEMS.register("jetpack_diamond", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_POTATO = ITEMS.register("jetpack_potato", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_CREATIVE = ITEMS.register("jetpack_creative", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));*/

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

    // BLOCKS:
}
