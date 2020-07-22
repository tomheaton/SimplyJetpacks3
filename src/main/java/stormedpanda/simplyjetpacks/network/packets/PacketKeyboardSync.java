package stormedpanda.simplyjetpacks.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import stormedpanda.simplyjetpacks.SyncHandler;

import java.util.function.Supplier;

public class PacketKeyboardSync {
    public boolean flyState;
    public boolean descendState;
    public boolean forwardState;
    public boolean backwardState;
    public boolean leftState;
    public boolean rightState;

    public PacketKeyboardSync(boolean flyState, boolean descendState, boolean forwardState, boolean backwardState, boolean leftState, boolean rightState) {
        this.flyState = flyState;
        this.descendState = descendState;
        this.forwardState = forwardState;
        this.backwardState = backwardState;
        this.leftState = leftState;
        this.rightState = rightState;
    }

    public static PacketKeyboardSync fromBytes(PacketBuffer buffer) {
        return new PacketKeyboardSync(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
    }

    public static void toBytes(PacketKeyboardSync message, PacketBuffer buf) {
        buf.writeBoolean(message.flyState);
        buf.writeBoolean(message.descendState);
        buf.writeBoolean(message.forwardState);
        buf.writeBoolean(message.backwardState);
        buf.writeBoolean(message.leftState);
        buf.writeBoolean(message.rightState);
    }

    public static void handle(PacketKeyboardSync message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                //SimplyJetpacks.LOGGER.info("running updater");
                SyncHandler.update(player, message.flyState, message.descendState, message.forwardState, message.backwardState, message.leftState, message.rightState);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}