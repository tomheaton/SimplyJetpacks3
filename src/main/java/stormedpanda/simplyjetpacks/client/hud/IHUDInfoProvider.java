package stormedpanda.simplyjetpacks.client.hud;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IHUDInfoProvider {
    void addHUDInfo(List<ITextComponent> list, ItemStack stack);
}
