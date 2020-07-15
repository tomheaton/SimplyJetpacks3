package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import stormedpanda.simplyjetpacks.RegistryHandler;

public class TestModScreen extends ContainerScreen<TestModContainer> {

    public static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/basic_gui.png");

    public TestModScreen(TestModContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override // render
    protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        //this.renderBackground();
        super.render(stack, mouseX, mouseY, partialTicks);
        //this.renderHoveredToolTip(mouseX, mouseY);
    }

    public static void open() {
        //Minecraft.getInstance().displayGuiScreen(new TestModScreen(RegistryHandler.MY_CONTAINER, inv, new StringTextComponent("TITLE HERE")));
    }


/*    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }*/

/*    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.drawString(Minecraft.getInstance().fontRenderer, "Energy: ", 10, 10, 0xffffff);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }*/
}
