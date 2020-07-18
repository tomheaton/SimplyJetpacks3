package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;
import stormedpanda.simplyjetpacks.network.NetworkHandler;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleEngine;
import stormedpanda.simplyjetpacks.network.packets.PacketToggleHover;

public class JetpackGuiScreen extends Screen {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 100;

    private ResourceLocation GUI = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/jetpack_gui_pack.png");

    public JetpackGuiScreen() {
        super(new TranslationTextComponent("screen.simplyjetpacks.jetpack_gui.title"));
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    @Override
    protected void init() {
        //int relY = (this.height - HEIGHT) / 2;
        int relX = (this.width - WIDTH) / 2;
        //int relX = width / 2;
        int relY = (this.height - HEIGHT) / 2;
        //int relY = height / 2;;

        addButton(new Button(relX + 10, relY + 10, 60, 20, new StringTextComponent("Engine"), button -> NetworkHandler.sendToServer(new PacketToggleEngine())));
        addButton(new Button(relX + 10, relY + 30, 60, 20, new StringTextComponent("HoverMode"), button -> NetworkHandler.sendToServer(new PacketToggleHover())));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

/*    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        for(int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }
    }*/

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(stack, relX, relY, 0, 0, WIDTH, HEIGHT);
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new JetpackGuiScreen());
    }
}