package stormedpanda.simplyjetpacks.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ObjectHolder;
import stormedpanda.simplyjetpacks.RegistryHandler;

public class TestContainer extends Container {

    public final PlayerInventory inv;

    @ObjectHolder("simplyjetpacks:test_container")
    public static ContainerType<TestContainer> TYPE;

    public TestContainer(int id, PlayerInventory inv) {
        super(RegistryHandler.TEST_CONTAINER.get(), id);
        this.inv = inv;
        bindPlayerInventory(inv, 1);
    }

    private void bindPlayerInventory(IInventory playerInventory, int blockedSlot) {
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                int index = j1 + l * 9 + 9;
                this.addSlot(new Slot(playerInventory, index, 8 + j1 * 18, l * 18 + 51));
            }
        }


    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        /*if (!playerIn.world.isRemote)
            BeltFinder.sendSync(playerIn);*/
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 1) {
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
