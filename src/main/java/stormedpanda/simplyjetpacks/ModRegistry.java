package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.client.models.ModelDragonArmor;
import stormedpanda.simplyjetpacks.client.models.ModelJetpack;
import stormedpanda.simplyjetpacks.items.ItemDragonArmor;
import stormedpanda.simplyjetpacks.items.ItemJetpack;
import stormedpanda.simplyjetpacks.items.TestItemJetpack;
import stormedpanda.simplyjetpacks.lists.ListArmorMaterial;
import stormedpanda.simplyjetpacks.lists.ItemList;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

	public static final Logger LOGGER = SimplyJetpacks.LOGGER;
	public static final ItemGroup tabSimplyJetpacks = SimplyJetpacks.tabSimplyJetpacks;

	private static String location(String name) { return SimplyJetpacks.MODID + ":" + name; }

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll (
				// ARMOR:
				ItemList.dragon_helmet = new ItemDragonArmor(ListArmorMaterial.DRAGON, EquipmentSlotType.HEAD, new Item.Properties().group(tabSimplyJetpacks), new ModelDragonArmor()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/dragon_armor.png")).setRegistryName(location("dragon_helmet")),
				ItemList.dragon_chestplate = new ItemDragonArmor(ListArmorMaterial.DRAGON, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelDragonArmor()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/dragon_armor.png")).setRegistryName(location("dragon_chestplate")),
				ItemList.dragon_leggings = new ItemDragonArmor(ListArmorMaterial.DRAGON, EquipmentSlotType.LEGS, new Item.Properties().group(tabSimplyJetpacks), new ModelDragonArmor()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/dragon_armor.png")).setRegistryName(location("dragon_leggings")),
				ItemList.dragon_boots = new ItemDragonArmor(ListArmorMaterial.DRAGON, EquipmentSlotType.FEET, new Item.Properties().group(tabSimplyJetpacks), new ModelDragonArmor()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/dragon_armor.png")).setRegistryName(location("dragon_boots")),

				// TESTING:

				// JETPACKS:
				ItemList.jetpack_iron = new ItemJetpack("jetpack_iron", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_iron.png")).setRegistryName(location("jetpack_iron")),
				ItemList.jetpack_iron_armored = new ItemJetpack("jetpack_iron_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_iron_armored.png")).setRegistryName(location("jetpack_iron_armored")),
				ItemList.jetpack_gold = new ItemJetpack("jetpack_gold", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_gold.png")).setRegistryName(location("jetpack_gold")),
				ItemList.jetpack_gold_armored = new ItemJetpack("jetpack_gold_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_gold_armored.png")).setRegistryName(location("jetpack_gold_armored")),
				ItemList.jetpack_diamond = new ItemJetpack("jetpack_diamond", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_diamond.png")).setRegistryName(location("jetpack_diamond")),
				ItemList.jetpack_diamond_armored = new ItemJetpack("jetpack_diamond_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_diamond_armored.png")).setRegistryName(location("jetpack_diamond_armored")),
				ItemList.jetpack_netherite = new TestItemJetpack("jetpack_netherite", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_netherite.png")).setRegistryName(location("jetpack_netherite")),
				ItemList.jetpack_netherite_armored = new TestItemJetpack("jetpack_netherite_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_netherite_armored.png")).setRegistryName(location("jetpack_netherite_armored")),

				ItemList.jetpack_creative = new ItemJetpack("jetpack_creative", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_creative.png")).setRegistryName(location("jetpack_creative")),
				ItemList.jetpack_creative_armored = new ItemJetpack("jetpack_creative_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_creative_armored.png")).setRegistryName(location("jetpack_creative_armored")),

				ItemList.jetpack_test = new TestItemJetpack("jetpack_test", ListArmorMaterial.JETPACK, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_test.png")).setRegistryName(location("jetpack_test")),
				ItemList.jetpack_test_armored = new ItemJetpack("jetpack_test_armored", ListArmorMaterial.JETPACK_ARMORED, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ModelJetpack()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_test_armored.png")).setRegistryName(location("jetpack_test_armored"))
		);
		LOGGER.info("Items registered.");
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll( );
		LOGGER.info("Blocks registered.");
	}
}