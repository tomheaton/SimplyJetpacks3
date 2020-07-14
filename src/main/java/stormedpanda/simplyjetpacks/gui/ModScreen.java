package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

import javax.annotation.Nonnull;

public class ModScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/config.png");
    private static final ResourceLocation SCENE = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/scene.png");

    private static final int WIDTH = 230;
    private static final int HEIGHT = 230;

    private int guiLeft;
    private int guiTop;

    public ModScreen() {
        super(new StringTextComponent("My Mod Screen"));
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        guiLeft = (this.width - WIDTH - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;
    }

    @Override
    public void render(@Nonnull MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderBackground(stack);
        minecraft.getTextureManager().bindTexture(BACKGROUND);
        Matrix4f matrix = stack.getLast().getMatrix();
        //drawTexturedModalRect(matrix, guiLeft + WIDTH, guiTop, 0, 0, WIDTH, HEIGHT);
        minecraft.getTextureManager().bindTexture(SCENE);
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(BACKGROUND);
        //drawTexturedModalRect(matrix, guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
        drawGuiContainerForegroundLayer(stack,mouseX, mouseY);
    }

    //Override
    public void drawGuiContainerForegroundLayer(MatrixStack stack, int mouseX, int mouseY) {
        this.font.drawString(stack, "Text here", 16, 10, 994);
    }


    public void renderBackground(MatrixStack stack, int vOffset) {
        if (this.minecraft.world != null) {
            this.fillGradient(stack, 1, 1, this.width, this.height, -1072689136, -804253680);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, stack));
        } else {
            this.renderDirtBackground(vOffset);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.displayGuiScreen((ModScreen)null);
    }
}
