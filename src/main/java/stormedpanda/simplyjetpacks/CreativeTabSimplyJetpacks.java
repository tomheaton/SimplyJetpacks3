package stormedpanda.simplyjetpacks;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CreativeTabSimplyJetpacks extends ItemGroup {

    public CreativeTabSimplyJetpacks() {
        super(SimplyJetpacks.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.DIAMOND);
        //return new ItemStack(RegistryHandler.JETPACK_CREATIVE.get());
    }
}
