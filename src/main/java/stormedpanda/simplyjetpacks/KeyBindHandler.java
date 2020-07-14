package stormedpanda.simplyjetpacks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import stormedpanda.simplyjetpacks.gui.ModScreen;

@Mod.EventBusSubscriber(modid = SimplyJetpacks.MODID)
public class KeyBindHandler {

    public static KeyBinding GUI_KEY;

    public static void setup() {
        GUI_KEY = new KeyBinding("keybind.simplyjetpacks.jetpack_gui", GLFW.GLFW_KEY_K, "key.categories.simplyjetpacks");
        ClientRegistry.registerKeyBinding(GUI_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if(GUI_KEY.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new ModScreen());
        }
    }
}
