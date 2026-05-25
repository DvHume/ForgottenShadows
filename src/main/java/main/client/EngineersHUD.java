package main.client;

import api.IEnergyTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "frs", value = Dist.CLIENT)
public class EngineersHUD {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        if (mc.hitResult != null && mc.hitResult.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockHit = (BlockRayTraceResult) mc.hitResult;
            TileEntity tile = mc.level.getBlockEntity(blockHit.getBlockPos());

            if (tile instanceof IEnergyTile) {
                IEnergyTile energyTile = (IEnergyTile) tile;
                int currentEnergy = energyTile.getStoredEnergy();
                int maxEnergy = energyTile.getMaxEnergy();

                ITextComponent textComponent = new TranslationTextComponent(
                        "hud.frs.energy_info",
                        currentEnergy,
                        maxEnergy
                );
                String text = textComponent.getString();
                MatrixStack matrixStack = event.getMatrixStack();
                FontRenderer font = mc.font;

                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();

                int x = width / 2 + 10;
                int y = height / 2 + 10;

                mc.gui.fill(matrixStack, x - 4, y - 4, x + font.width(text) + 4, y + 12, 0x77000000);
                font.draw(matrixStack, text, x, y, 0xFFFFFF);
            }
        }
    }
}
