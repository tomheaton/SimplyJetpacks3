package stormedpanda.simplyjetpacks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.items.ItemJetpack;
import stormedpanda.simplyjetpacks.network.NetworkHandler;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleGui;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindHandler {

    public static KeyBinding JETPACK_GUI_KEY;
    public static KeyBinding JETPACK_ENGINE_KEY;
    public static KeyBinding JETPACK_HOVER_KEY;

    public static void setup() {
        JETPACK_GUI_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_gui", GLFW.GLFW_KEY_K, "keybind.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(JETPACK_GUI_KEY);
        JETPACK_ENGINE_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_engine", GLFW.GLFW_KEY_J, "keybind.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(JETPACK_ENGINE_KEY);
        JETPACK_HOVER_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_hover", GLFW.GLFW_KEY_J, "keybind.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(JETPACK_HOVER_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack chestStack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        Item chestItem = null;
        ItemJetpack jetpack;
        if(!chestStack.isEmpty()) { chestItem = chestStack.getItem(); }

        if (chestItem instanceof ItemJetpack) {
            jetpack = (ItemJetpack) chestItem;
            if(JETPACK_GUI_KEY.isPressed()) {
                SimplyJetpacks.LOGGER.info("Jetpack GUI key pressed");
                NetworkHandler.sendToServer(new PacketToggleGui());
            }
            if (JETPACK_ENGINE_KEY.isPressed()) {
                SimplyJetpacks.LOGGER.info("Jetpack Engine key pressed");
                //jetpack.toggleState(jetpack.isOn(chestStack), chestStack, null, jetpack.TAG_ON, player, SimplyJetpacksConfig.enableStateMessages);
            }
            if (JETPACK_HOVER_KEY.isPressed()) {
                SimplyJetpacks.LOGGER.info("Jetpack Hover key pressed");
                //jetpack.toggleState(jetpack.isOn(chestStack), chestStack, null, jetpack.TAG_ON, player, SimplyJetpacksConfig.enableStateMessages);
            }
        }
    }
}
