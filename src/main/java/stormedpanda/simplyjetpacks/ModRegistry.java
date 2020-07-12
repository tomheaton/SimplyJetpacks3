package stormedpanda.simplyjetpacks;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.client.models.ArmorTest;
import stormedpanda.simplyjetpacks.client.models.JetpackTest;
import stormedpanda.simplyjetpacks.items.ModdedArmorItem;
import stormedpanda.simplyjetpacks.lists.ArmorMaterialList;
import stormedpanda.simplyjetpacks.lists.ItemList;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

	public static final String MODID = SimplyJetpacks.MODID;
	public static final Logger LOGGER = SimplyJetpacks.LOGGER;
	public static final ItemGroup tabSimplyJetpacks = SimplyJetpacks.tabSimplyJetpacks;
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll
		(
				// EXAMPLES:
				ItemList.netherite_helmet = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.HEAD, new Item.Properties().group(tabSimplyJetpacks), new ArmorTest()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/custom_armor.png")).setRegistryName(location("netherite_helmet")),
				ItemList.netherite_chestplate = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new ArmorTest()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/custom_armor.png")).setRegistryName(location("netherite_chestplate")),
				ItemList.netherite_leggings = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.LEGS, new Item.Properties().group(tabSimplyJetpacks), new ArmorTest()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/custom_armor.png")).setRegistryName(location("netherite_leggings")),
				ItemList.netherite_boots = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.FEET, new Item.Properties().group(tabSimplyJetpacks), new ArmorTest()::applyData, new ResourceLocation("simplyjetpacks:textures/models/armor/custom_armor.png")).setRegistryName(location("netherite_boots")),

				// TESTING:

				// JETPACKS:
				ItemList.jetpack_iron = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new JetpackTest()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_iron.png")).setRegistryName(location("jetpack_iron")),
				ItemList.jetpack_gold = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new JetpackTest()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_gold.png")).setRegistryName(location("jetpack_gold")),
				ItemList.jetpack_diamond = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new JetpackTest()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_diamond.png")).setRegistryName(location("jetpack_diamond")),
				ItemList.jetpack_creative = new ModdedArmorItem(ArmorMaterialList.netherite, EquipmentSlotType.CHEST, new Item.Properties().group(tabSimplyJetpacks), new JetpackTest()::applyData, new ResourceLocation("simplyjetpacks:textures/armor/jetpack_creative.png")).setRegistryName(location("jetpack_creative"))

		);
		LOGGER.info("Items registered.");
	}
	
	public static ResourceLocation location(String name)
	{
		return new ResourceLocation(MODID, name);
	}
}