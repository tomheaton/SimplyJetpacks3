package stormedpanda.simplyjetpacks.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import stormedpanda.simplyjetpacks.gui.JetpackGuiScreen;

import java.util.function.Supplier;

public class PacketToggleGui {

    public PacketToggleGui(PacketBuffer buf) {
    }

    public void toBytes(PacketBuffer buf) {
    }

    public PacketToggleGui() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(JetpackGuiScreen::open);
        //ctx.get().enqueueWork(TestGuiScreen::open);
        ctx.get().setPacketHandled(true);
    }
}
