package stormedpanda.simplyjetpacks;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import stormedpanda.simplyjetpacks.lists.ItemList;

public class CreativeTabSimplyJetpacks extends ItemGroup {

    public CreativeTabSimplyJetpacks() {
        super(SimplyJetpacks.MODID + ".main");
    }

    @Override
    public ItemStack createIcon() {
        //return new ItemStack(Items.DIAMOND);
        //return new ItemStack(RegistryHandler.JETPACK_CREATIVE.get());
        return new ItemStack(ItemList.jetpack_creative.getItem());
    }
}
