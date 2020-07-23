package stormedpanda.simplyjetpacks.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.lists.ArmorMaterialList;

public class PilotGogglesItem extends ArmorItem {

    public PilotGogglesItem() {
        super(ArmorMaterialList.PILOT_GOGGLES, EquipmentSlotType.HEAD, new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks));
    }
}
