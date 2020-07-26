package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import stormedpanda.simplyjetpacks.handlers.KeybindHandler;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.network.NetworkHandler;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleEHover;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleEngine;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleHover;

public class JetpackGuiScreen extends Screen {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 100;
    private float mousePosX;
    private float mousePosY;

    private ResourceLocation GUI = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/jetpack_gui_pack.png");

    public JetpackGuiScreen() {
        super(new TranslationTextComponent("screen.simplyjetpacks.jetpack_gui.title"));
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 108, relY + 10, 60, 20, new StringTextComponent("Engine"), button -> NetworkHandler.sendToServer(new PacketToggleEngine())));
        addButton(new Button(relX + 108, relY + 40, 60, 20, new StringTextComponent("Hover"), button -> NetworkHandler.sendToServer(new PacketToggleHover())));
        // TODO: disable button if jetpack cannot perform emergency hover
        addButton(new Button(relX + 68, relY + 70, 100, 20, new StringTextComponent("Emergency Hover"), button -> NetworkHandler.sendToServer(new PacketToggleEHover())));
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.mousePosX = (float) mouseX;
        this.mousePosY = (float) mouseY;
        this.blit(stack, relX, relY, 0, 0, WIDTH, HEIGHT);
        super.render(stack, mouseX, mouseY, partialTicks);
        //FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        //drawString(stack, fontRenderer, new StringTextComponent("ENGINE: "), relX + 80, relY + 70, 0xFFFFFF );
        //drawString(stack, fontRenderer, new StringTextComponent("HOVER: "), relX + 80, relY + 40, 0xFFFFFF );
        assert this.minecraft.player != null;
        InventoryScreen.drawEntityOnScreen(relX + 40, relY + 90, 40, (float)(relX + 51) - this.mousePosX, (float)(relY + 75 - 50) - this.mousePosY, this.minecraft.player);
    }

    public static void open() { Minecraft.getInstance().displayGuiScreen(new JetpackGuiScreen()); }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (p_231046_1_ == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        } else if (KeybindHandler.JETPACK_GUI_KEY.matchesKey(p_231046_1_, p_231046_2_)) {
            this.onClose();
            return true;
        } else if (p_231046_1_ == 258) {
            boolean flag = !hasShiftDown();
            if (!this.changeFocus(flag)) {
                this.changeFocus(flag);
            }
            return true;
        } else {
            return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
        }
    }
}