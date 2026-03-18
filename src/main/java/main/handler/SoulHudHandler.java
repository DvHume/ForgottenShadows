package main.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import main.capability.SoulCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class SoulHudHandler {
    //texture
    private static final ResourceLocation SOUL_BAR = new ResourceLocation("frs", "textures/gui/soul_bar.png");

    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 4;

    @SubscribeEvent
    public static void onRenderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (player == null) return;

        player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
            MatrixStack matrix = event.getMatrixStack();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            //position
            int x = 10;
            int y = screenHeight - 49;

            //
            AbstractGui.fill(matrix, x - 1, y -1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, 0xFF00BCD4);

            String text = I18n.get("hud.frs.souls") + ": " + (int)souls.getSouls();
            mc.font.draw(matrix, text, x, y -10, 0xFFADD8E6);
        });
    }
}
