package stormedpanda.simplyjetpacks.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import stormedpanda.simplyjetpacks.gui.GuiJetpackScreen;
import stormedpanda.simplyjetpacks.items.TestItemJetpack;

import java.util.function.Supplier;

public class PacketToggleHover {

    public PacketToggleHover(PacketBuffer buf) {
    }

    public void toBytes(PacketBuffer buf) {
    }

    public PacketToggleHover() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                Item item = stack.getItem();
                if (item instanceof TestItemJetpack) {
                    TestItemJetpack jetpack = (TestItemJetpack) item;
                    jetpack.toggleHover(stack);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
