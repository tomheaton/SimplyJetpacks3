package stormedpanda.simplyjetpacks.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import stormedpanda.simplyjetpacks.gui.TestContainer;

import java.util.function.Supplier;

public class PacketToggleTestGui {

    public PacketToggleTestGui(PacketBuffer buf) {
    }

    public void toBytes(PacketBuffer buf) {
    }

    public PacketToggleTestGui() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity player = ctx.get().getSender();
        if (player == null) { return; }
        ctx.get().enqueueWork(() -> NetworkHooks.openGui(player, new SimpleNamedContainerProvider(
                (id, inv, data) -> new TestContainer(id, inv), new StringTextComponent("test_container"))
        ));
        ctx.get().setPacketHandled(true);
    }
}
