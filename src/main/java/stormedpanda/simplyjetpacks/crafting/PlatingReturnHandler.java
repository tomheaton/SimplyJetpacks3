package stormedpanda.simplyjetpacks.crafting;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.items.Jetpack;
import stormedpanda.simplyjetpacks.items.ItemJetpack;

public class PlatingReturnHandler {

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent evt) {
        if (evt.getPlayer().world.isRemote || !(evt.getCrafting().getItem() instanceof ItemJetpack)) {
/*            if (!(evt.getCrafting().getItem() instanceof ItemFluxpack)) {
                return;
            }*/
            return;
        }

        if (evt.getCrafting().getItem() instanceof ItemJetpack) {
            Jetpack outputPack = Jetpack.getTypeFromMeta(evt.getCrafting().getItem().getMaxDamage(evt.getCrafting()));
            SimplyJetpacks.LOGGER.info(outputPack.getString());
            if (outputPack.getIsArmored()) {
                return;
            }
            for (int i = 0; i < evt.getInventory().getSizeInventory(); i++) {
                ItemStack input = evt.getInventory().getStackInSlot(i);
                if (input == null || !(input.getItem() instanceof ItemJetpack)) {
                    continue;
                }
                Jetpack inputPack = Jetpack.getTypeFromMeta(evt.getCrafting().getItem().getMaxDamage(input));
                if (inputPack != null && inputPack.isArmored) {

                    //ItemEntity item = evt.getPlayer().entityDropItem(new ItemStack(ModItems.metaItemMods, 1, inputPack.getPlatingMeta()), 0.0F);
                    ItemEntity item = evt.getPlayer().entityDropItem(new ItemStack(Items.DIAMOND, 64), 0.0F);
                    item.setNoPickupDelay();
                    break;
                }
            }
        }
    }
}
