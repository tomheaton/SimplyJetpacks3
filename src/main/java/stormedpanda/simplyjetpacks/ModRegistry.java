package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import stormedpanda.simplyjetpacks.items.JetpackType;
import stormedpanda.simplyjetpacks.lists.ItemList;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

	public static final Logger LOGGER = SimplyJetpacks.LOGGER;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll (
				// Vanilla:
				ItemList.jetpack_iron = new JetpackItem("jetpack_iron", JetpackType.IRON).setRegistryName("jetpack_iron"),
				ItemList.jetpack_iron_armored = new JetpackItem("jetpack_iron_armored", JetpackType.IRON_ARMORED).setRegistryName("jetpack_iron_armored"),
				ItemList.jetpack_gold = new JetpackItem("jetpack_gold", JetpackType.GOLD).setRegistryName("jetpack_gold"),
				ItemList.jetpack_gold_armored = new JetpackItem("jetpack_gold_armored", JetpackType.GOLD_ARMORED).setRegistryName("jetpack_gold_armored"),
				ItemList.jetpack_diamond = new JetpackItem("jetpack_diamond", JetpackType.DIAMOND).setRegistryName("jetpack_diamond"),
				ItemList.jetpack_diamond_armored = new JetpackItem("jetpack_diamond_armored", JetpackType.DIAMOND_ARMORED).setRegistryName("jetpack_diamond_armored"),
				ItemList.jetpack_netherite = new JetpackItem("jetpack_netherite", JetpackType.NETHERITE).setRegistryName("jetpack_netherite"),
				ItemList.jetpack_netherite_armored = new JetpackItem("jetpack_netherite_armored",JetpackType.NETHERITE_ARMORED).setRegistryName("jetpack_netherite_armored"),
				ItemList.jetpack_creative = new JetpackItem("jetpack_creative", JetpackType.CREATIVE).setRegistryName("jetpack_creative"),
				ItemList.jetpack_creative_armored = new JetpackItem("jetpack_creative_armored", JetpackType.CREATIVE_ARMORED).setRegistryName("jetpack_creative_armored"),
				ItemList.jetpack_test = new JetpackItem("jetpack_test", JetpackType.TEST).setRegistryName("jetpack_test"),
				ItemList.jetpack_test_armored = new JetpackItem("jetpack_test_armored", JetpackType.TEST_ARMORED).setRegistryName("jetpack_test_armored"),

				// Immersive Engineering:
				ItemList.jetpack_ie1 = new JetpackItem("jetpack_ie1", JetpackType.IE1).setRegistryName("jetpack_ie1"),
				ItemList.jetpack_ie1_armored = new JetpackItem("jetpack_ie1_armored", JetpackType.IE1_ARMORED).setRegistryName("jetpack_ie1_armored"),
				ItemList.jetpack_ie2 = new JetpackItem("jetpack_ie2", JetpackType.IE2).setRegistryName("jetpack_ie2"),
				ItemList.jetpack_ie2_armored = new JetpackItem("jetpack_ie2_armored", JetpackType.IE2_ARMORED).setRegistryName("jetpack_ie2_armored"),
				ItemList.jetpack_ie3 = new JetpackItem("jetpack_ie3", JetpackType.IE3).setRegistryName("jetpack_ie3"),
				ItemList.jetpack_ie3_armored = new JetpackItem("jetpack_ie3_armored", JetpackType.IE3_ARMORED).setRegistryName("jetpack_ie3_armored"),

				// Mekanism:
				ItemList.jetpack_mek1 = new JetpackItem("jetpack_mek1", JetpackType.MEK1).setRegistryName("jetpack_mek1"),
				ItemList.jetpack_mek1_armored = new JetpackItem("jetpack_mek1_armored", JetpackType.MEK1_ARMORED).setRegistryName("jetpack_mek1_armored"),
				ItemList.jetpack_mek2 = new JetpackItem("jetpack_mek2", JetpackType.MEK2).setRegistryName("jetpack_mek2"),
				ItemList.jetpack_mek2_armored = new JetpackItem("jetpack_mek2_armored", JetpackType.MEK2_ARMORED).setRegistryName("jetpack_mek2_armored"),
				ItemList.jetpack_mek3 = new JetpackItem("jetpack_mek3", JetpackType.MEK3).setRegistryName("jetpack_mek3"),
				ItemList.jetpack_mek3_armored = new JetpackItem("jetpack_mek3_armored", JetpackType.MEK3_ARMORED).setRegistryName("jetpack_mek3_armored"),
				ItemList.jetpack_mek4 = new JetpackItem("jetpack_mek4", JetpackType.MEK4).setRegistryName("jetpack_mek4"),
				ItemList.jetpack_mek4_armored = new JetpackItem("jetpack_mek4_armored", JetpackType.MEK4_ARMORED).setRegistryName("jetpack_mek4_armored")
		);
		LOGGER.info("Items registered.");
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll( );
		LOGGER.info("Blocks registered.");
	}
}