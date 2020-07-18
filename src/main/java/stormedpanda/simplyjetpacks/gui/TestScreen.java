package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

public class TestScreen extends ContainerScreen<TestContainer> {

    private ResourceLocation GUI_TEXTURE = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/jetpack_gui_pack.png");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 100;

    private float mousePosX;
    private float mousePosY;

    public TestScreen(TestContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
        this.xSize = 176;
        this.ySize = 133;
        this.field_238745_s_ = this.ySize - 94;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.mousePosX = (float) mouseX;
        this.mousePosY = (float) mouseY;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        int slots = 1;
        int width = slots * 18;
        int x = 7 + ((9 - slots) * 18) / 2;
        this.blit(matrixStack,i + x, j + 19, 0, this.ySize, width, 18);

        assert this.minecraft.player != null;
        InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosX, (float)(j + 75 - 50) - this.mousePosY, this.minecraft.player);
    }
}
