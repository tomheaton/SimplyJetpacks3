package stormedpanda.simplyjetpacks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.glfw.GLFW;
import stormedpanda.simplyjetpacks.config.SimplyJetpacksConfig;
import stormedpanda.simplyjetpacks.gui.TestModContainer;
import stormedpanda.simplyjetpacks.items.ItemJetpack;
import stormedpanda.simplyjetpacks.network.Networking;
import stormedpanda.simplyjetpacks.network.PacketOpenGui;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindHandler {

    public static KeyBinding GUI_KEY;
    public static KeyBinding JETPACK_POWER_KEY;

    public static void setup() {
        GUI_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_gui", GLFW.GLFW_KEY_K, "keybind.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(GUI_KEY);
        JETPACK_POWER_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_power", GLFW.GLFW_KEY_J, "keybind.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(JETPACK_POWER_KEY);
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
            if(GUI_KEY.isPressed()) {
                Networking.sendToServer(new PacketOpenGui());
            }
            if (JETPACK_POWER_KEY.isPressed()) {
                jetpack.toggleState(jetpack.isOn(chestStack), chestStack, null, jetpack.TAG_ON, player, SimplyJetpacksConfig.enableStateMessages);
            }
        }
    }
}
