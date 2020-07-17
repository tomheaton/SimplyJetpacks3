package stormedpanda.simplyjetpacks.client;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IHUDInfoProvider {

    //void addHUDInfo(List<String> list, ItemStack stack, boolean showFuel, boolean showState);
    //void addHUDStrings(List<ITextComponent> list, ItemStack stack, EquipmentSlotType slotType);

    void addHUDInfo(List<ITextComponent> list, ItemStack stack);
}
