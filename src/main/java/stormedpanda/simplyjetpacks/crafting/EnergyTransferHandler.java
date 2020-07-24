package stormedpanda.simplyjetpacks.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import stormedpanda.simplyjetpacks.items.JetpackType;
import stormedpanda.simplyjetpacks.util.NBTHelper;

public class EnergyTransferHandler {

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        int storedEnergy = 0;
        ItemStack craftedStack = event.getCrafting();
        Item craftedItem = event.getCrafting().getItem();

        if (craftedItem instanceof JetpackItem) {
            for (int i = 0; i < event.getInventory().getSizeInventory(); i++) {
                ItemStack input = event.getInventory().getStackInSlot(i);
                if (input == null || !(input.getItem() instanceof JetpackItem)) { continue; }

                if (input.getItem() instanceof JetpackItem) {
                    JetpackType inputJetpack = ((JetpackItem) input.getItem()).getType();
                    storedEnergy = NBTHelper.getInt(input, "Energy");
                    SimplyJetpacks.LOGGER.info("Stored energy: " + storedEnergy);
                    int energyToTransfer = Math.min(storedEnergy, ((JetpackItem) craftedItem).getEnergyStored(craftedStack));
                    //NBTHelper.setInt(craftedStack, "Energy", energyToTransfer);
                    NBTHelper.setInt(craftedStack, "Energy", storedEnergy);
                    break;
                }
            }
        }
    }
}
