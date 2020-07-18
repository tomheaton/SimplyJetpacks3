package stormedpanda.simplyjetpacks.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
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
        bindPlayerInventory(inv);
        addSlots();
    }

    private void addSlots() {
        Slot mySlot = new Slot(inv, 69, 50, 50);
        mySlot.putStack(this.inv.armorInventory.get(EquipmentSlotType.CHEST.getIndex()));
        addSlot(new Slot(inv, 69, 20, 20));
        addSlot(mySlot);

    }
    private void bindPlayerInventory(IInventory playerInventory) {
        this.addSlot(new Slot(playerInventory, 69, 8 + 24 * 18, 5 * 18 + 51));
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
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
