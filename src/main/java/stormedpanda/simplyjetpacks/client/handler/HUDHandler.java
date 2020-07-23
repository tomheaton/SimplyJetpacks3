package stormedpanda.simplyjetpacks.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.items.JetpackItem;
import java.util.ArrayList;
import java.util.List;

public class HUDHandler {

    public final Minecraft minecraft = Minecraft.getInstance();
    public static final Logger LOGGER = SimplyJetpacks.LOGGER;

    //private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/hud.png");

    private void drawString(MainWindow window, MatrixStack matrix, ITextComponent text, int x, int y, int color) {
        FontRenderer font = minecraft.fontRenderer;
        int windowScaleHeight = window.getScaledHeight();
        int windowScaleWidth = window.getScaledWidth();
        font.func_238407_a_(matrix, text, x, y, color);
    }

    @SubscribeEvent()
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (minecraft.player != null) {
            ItemStack chestplate = minecraft.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            Item item = chestplate.getItem();

            if (!chestplate.isEmpty() && item instanceof JetpackItem) {
                JetpackItem jetpack = (JetpackItem) item;

                IHUDInfoProvider provider = (IHUDInfoProvider) chestplate.getItem();

                List<ITextComponent> renderStrings = new ArrayList<>();
                provider.addHUDInfo(renderStrings, chestplate);
                if (renderStrings.isEmpty()) {
                    LOGGER.error("EMPTY");
                    return;
                }
                int count = 10;

                MatrixStack matrix = event.getMatrixStack();
                matrix.push();
                matrix.scale(1.0F, 1.0F, 1.0F);
                MainWindow window = event.getWindow();
                for (ITextComponent text : renderStrings) {
                    drawString(window, matrix, text, 10, count, 0x10F4D3);
                    count += 10;
                }
                matrix.pop();
            }
        }
    }
}
