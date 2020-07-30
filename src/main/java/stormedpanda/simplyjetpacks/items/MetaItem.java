package stormedpanda.simplyjetpacks.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.integration.IntegrationList;

public class MetaItem extends Item {

    public String fromMod;

    public MetaItem(String fromMod) {
        super(new Item.Properties().group(SimplyJetpacks.tabSimplyJetpacks));
        this.fromMod = fromMod;
    }

    @Override
    protected boolean isInGroup(ItemGroup group) {
        switch (fromMod) {
            case "vanilla":
                return IntegrationList.integrateVanilla && super.isInGroup(group);
            case "ie":
                return IntegrationList.integrateImmersiveEngineering && super.isInGroup(group);
            case "mek":
                return IntegrationList.integrateMekanism && super.isInGroup(group);
            default:
                return super.isInGroup(group);
        }
    }
}
