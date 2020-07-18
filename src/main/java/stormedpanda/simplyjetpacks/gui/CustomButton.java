package stormedpanda.simplyjetpacks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import stormedpanda.simplyjetpacks.SimplyJetpacks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomButton extends Button {

    private final Runnable onLeftClick;

    protected final Button.IPressable onPress;
    protected final Button.ITooltip onTooltip;

    private ResourceLocation BUTTON_IMAGES = new ResourceLocation(SimplyJetpacks.MODID, "textures/gui/jetpack_gui_pack.png");
    private final int textureWidth;
    private final int textureHeight;

    public CustomButton(int x, int y, int width, int height, ITextComponent title, Runnable onLeftClick, @Nullable IPressable onPress, @Nullable ITooltip onTooltip, int textureWidth, int textureHeight) {
        super(x, y, width, height, title, onPress, onTooltip);
        this.onLeftClick = onLeftClick;
        this.onPress = onPress;
        this.onTooltip = onTooltip;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    private void onLeftClick() {
        if (onLeftClick != null) {
            onLeftClick.run();
        }
    }

    //Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        Minecraft.getInstance().getTextureManager().bindTexture(BUTTON_IMAGES);

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
        blit(matrixStack, x, y, width, height, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
    }
/*
    public void drawBackground(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
        this.minecraft.getTextureManager().bindTexture(BUTTON_IMAGES);
        blit(matrix, x, y, width, height, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
    }*/
}
