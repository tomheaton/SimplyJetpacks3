package stormedpanda.simplyjetpacks;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stormedpanda.simplyjetpacks.items.ItemJetpack;
import stormedpanda.simplyjetpacks.items.ModArmorItem;
import stormedpanda.simplyjetpacks.models.ModelJetpack;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyJetpacks.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // TESTING:
    public static final ModelJetpack jetpackModel = new ModelJetpack();

    public static final RegistryObject<Item> JETPACK_TEST = ITEMS.register("jetpack_test", () ->
            new ItemJetpack(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    public static final RegistryObject<Item> CHESTPLATE_TEST = ITEMS.register("chestplate_test", () ->
                    new ModArmorItem(EquipmentSlotType.CHEST, new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks), jetpackModel::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_test.png")));
                    //new ModArmorItem(EquipmentSlotType slot, Properties builder, BiFunction<BipedModel, EquipmentSlotType, BipedModel<?>> armorApplier, ResourceLocation armorTexture));

    // JETPACKS:
    public static final RegistryObject<Item> JETPACK_IRON = ITEMS.register("jetpack_iron", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_GOLD = ITEMS.register("jetpack_gold", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_DIAMOND = ITEMS.register("jetpack_diamond", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_POTATO = ITEMS.register("jetpack_potato", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> JETPACK_CREATIVE = ITEMS.register("jetpack_creative", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    // THRUSTERS:
    public static final RegistryObject<Item> THRUSTER_IRON = ITEMS.register("thruster_iron", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_GOLD = ITEMS.register("thruster_gold", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));
    public static final RegistryObject<Item> THRUSTER_DIAMOND = ITEMS.register("thruster_diamond", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

    // MISC:
    public static final RegistryObject<Item> LEATHER_STRAP = ITEMS.register("leather_strap", () ->
            new Item(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks)));

}
