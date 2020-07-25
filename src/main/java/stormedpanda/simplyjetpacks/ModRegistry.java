package stormedpanda.simplyjetpacks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import stormedpanda.simplyjetpacks.items.JetpackType;
import stormedpanda.simplyjetpacks.lists.ItemList;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll (
				// Simply Jetpacks:
				ItemList.jetpack_creative = new JetpackItem("jetpack_creative", JetpackType.CREATIVE).setRegistryName("jetpack_creative"),
				ItemList.jetpack_creative_armored = new JetpackItem("jetpack_creative_armored", JetpackType.CREATIVE_ARMORED).setRegistryName("jetpack_creative_armored"),

				// Vanilla:
				ItemList.jetpack_vanilla1 = new JetpackItem("jetpack_vanilla1", JetpackType.VANILLA1).setRegistryName("jetpack_vanilla1"),
				ItemList.jetpack_vanilla1_armored = new JetpackItem("jetpack_vanilla1_armored", JetpackType.VANILLA1_ARMORED).setRegistryName("jetpack_vanilla1_armored"),
				ItemList.jetpack_vanilla2 = new JetpackItem("jetpack_vanilla2", JetpackType.VANILLA2).setRegistryName("jetpack_vanilla2"),
				ItemList.jetpack_vanilla2_armored = new JetpackItem("jetpack_vanilla2_armored", JetpackType.VANILLA2_ARMORED).setRegistryName("jetpack_vanilla2_armored"),
				ItemList.jetpack_vanilla3 = new JetpackItem("jetpack_vanilla3", JetpackType.VANILLA3).setRegistryName("jetpack_vanilla3"),
				ItemList.jetpack_vanilla3_armored = new JetpackItem("jetpack_vanilla3_armored", JetpackType.VANILLA3_ARMORED).setRegistryName("jetpack_vanilla3_armored"),
				ItemList.jetpack_vanilla4 = new JetpackItem("jetpack_vanilla4", JetpackType.VANILLA4).setRegistryName("jetpack_vanilla4"),
				ItemList.jetpack_vanilla4_armored = new JetpackItem("jetpack_vanilla4_armored",JetpackType.VANILLA4_ARMORED).setRegistryName("jetpack_vanilla4_armored"),

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
		SimplyJetpacks.LOGGER.info("Items registered.");
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll( );
		SimplyJetpacks.LOGGER.info("Blocks registered.");
	}
}