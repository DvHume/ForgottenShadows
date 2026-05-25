package main.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import main.container.ChemistryTableContainer;
import main.init.ModNetwork;
import main.network.ChemistryCraftPacket;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ChemistryTableScreen extends ContainerScreen<ChemistryTableContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("frs", "textures/gui/container/chemistry_table.png");

    public ChemistryTableScreen(ChemistryTableContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.addButton(new ImageButton(x + 131, y + 34, 18, 18, 176, 34, 0, TEXTURE, 256, 256, (button -> {
            ModNetwork.sendToServer(new ChemistryCraftPacket(menu.tile.getBlockPos()));
        })));
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int arrowHeight = (this.menu.tile.progress * 28) / this.menu.tile.maxProgress;
        if (arrowHeight > 0) {
            this.blit(matrixStack, x + 118, y + 15, 176, 0, 9, arrowHeight, 256, 256);
        }

        int fuelWidth = (this.menu.tile.fuel * 18) / this.menu.tile.maxFuel;
        if (fuelWidth > 0) {
            this.blit(matrixStack, x + 60, y + 44, 176, 29, fuelWidth, 4, 256, 256);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}
