package stormedpanda.simplyjetpacks.client.handlers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.client.IHUDInfoProvider;
import stormedpanda.simplyjetpacks.items.TestItemJetpack;
import java.util.ArrayList;
import java.util.List;

public class HUDHandler {

    public final Minecraft minecraft = Minecraft.getInstance();
    public static final Logger LOGGER = SimplyJetpacks.LOGGER;

    private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/hud.png");

    private void drawString(MainWindow window, MatrixStack matrix, ITextComponent text, boolean leftSide, int y, int color) {
        FontRenderer font = minecraft.fontRenderer;
        if (leftSide) {
            font.func_238407_a_(matrix, text, 2, y, color);
        } else {
            int width = font.func_238414_a_(text) + 2;
            font.func_238407_a_(matrix, text, window.getScaledWidth() - width, y, color);
        }
    }

    @SubscribeEvent()
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (minecraft.player != null) {
            ItemStack chestplate = minecraft.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            Item item = chestplate.getItem();

            if (!chestplate.isEmpty() && item instanceof TestItemJetpack) {
                TestItemJetpack jetpack = (TestItemJetpack) item;

                IHUDInfoProvider provider = (IHUDInfoProvider) chestplate.getItem();

                List<ITextComponent> renderStrings = new ArrayList<>();
                provider.addHUDInfo(renderStrings, chestplate);
                if (renderStrings.isEmpty()) {
                    LOGGER.error("EMPTY");
                    return;
                }
                int start = (renderStrings.size() * 2) + (0 * 9);

                //StringTextComponent basicText = new StringTextComponent("Energy: " + jetpack.getEnergyStored(chestplate) + " FE");

                int count = 0;
                MatrixStack matrix = event.getMatrixStack();
                matrix.push();
                matrix.scale(0.6F, 0.6F, 0.6F);
                FontRenderer font = minecraft.fontRenderer;
                MainWindow window = event.getWindow();
                int y = window.getScaledHeight();
                for (ITextComponent text : renderStrings) {
                    //font.func_238407_a_(matrix, someText, 2, y, 0xc8c8c8);
                    //drawString(window, matrix, text, true, (int) (y * (1 / 0.6F)) - start, 0x10F4D3);
                    drawString(window, matrix, text, true, y + count, 0x10F4D3);
                    count += 10;
                }
                //font.func_238407_a_(matrix, basicText, 2, y, 0xc8c8c8);
                matrix.pop();
            }
        }
    }
}
