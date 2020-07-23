package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stormedpanda.simplyjetpacks.enchantments.EnchantmentFuelEfficiency;
import stormedpanda.simplyjetpacks.gui.TestContainer;
import stormedpanda.simplyjetpacks.items.PilotGogglesItem;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyJetpacks.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyJetpacks.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SimplyJetpacks.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SimplyJetpacks.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Enchantments:
    public static final RegistryObject<Enchantment> FUEL_EFFICIENCY = ENCHANTMENTS.register("fuel_efficiency", EnchantmentFuelEfficiency::new);

    // Containers:
    public static final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("test_container", () -> IForgeContainerType.create((id, inv, data) -> new TestContainer(id, inv)));

    // Vanilla:
    public static final RegistryObject<Item> PILOT_GOGGLES = ITEMS.register("pilot_goggles", PilotGogglesItem::new);

    public static final RegistryObject<Item> PARTICLE_NONE = ITEMS.register("particle_none", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> PARTICLE_DEFAULT = ITEMS.register("particle_default", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> PARTICLE_SMOKE = ITEMS.register("particle_smoke", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> PARTICLE_RAINBOW = ITEMS.register("particle_rainbow", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    public static final RegistryObject<Item> LEATHER_STRAP = ITEMS.register("leather_strap", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_POTATO = ITEMS.register("jetpack_potato", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_IRON = ITEMS.register("thruster_iron", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_GOLD = ITEMS.register("thruster_gold", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_DIAMOND = ITEMS.register("thruster_diamond", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_NETHERITE = ITEMS.register("thruster_netherite", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    // Immersive Engineering:
    public static final RegistryObject<Item> THRUSTER_IE1 = ITEMS.register("thruster_ie1", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_IE2 = ITEMS.register("thruster_ie2", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_IE3 = ITEMS.register("thruster_ie3", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> ARMORPLATING_IE1 = ITEMS.register("armorplating_ie1", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> ARMORPLATING_IE2 = ITEMS.register("armorplating_ie2", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> ARMORPLATING_IE3 = ITEMS.register("armorplating_ie3", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
}
