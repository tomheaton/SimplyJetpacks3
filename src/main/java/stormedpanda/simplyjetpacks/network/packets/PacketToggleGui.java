package stormedpanda.simplyjetpacks.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToggleGui {

    public PacketToggleGui(PacketBuffer buf) {
    }

    public void toBytes(PacketBuffer buf) {
    }

    public PacketToggleGui() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity player = ctx.get().getSender();
        //ctx.get().enqueueWork(JetpackGuiScreen::open);
        ctx.get().setPacketHandled(true);
    }
}
