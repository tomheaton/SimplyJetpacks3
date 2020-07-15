package stormedpanda.simplyjetpacks.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import stormedpanda.simplyjetpacks.RegistryHandler;


public class TestModContainer extends Container {

    public TestModContainer(int id) {
        super(RegistryHandler.MY_CONTAINER.get(), id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}
