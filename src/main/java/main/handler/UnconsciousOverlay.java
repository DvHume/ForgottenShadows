/*package main.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class UnconsciousOverlay {

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        if (player == null) return;

        CompoundNBT nbt = player.getPersistentData();
        if (!nbt.getBoolean("isUnconscious")) return;

        long timePassed = player.level.getGameTime() - nbt.getLong("unconsciousTime");
        System.out.println("isUnconscious: " + nbt.getBoolean("isUnconscious") + "timePassed: " + timePassed);

        float alpha;
        if (timePassed < 40) {
            alpha = timePassed / 40.f;
        } else if (timePassed > UnconsciousHandler.UNCONSCIOUS_DURATION - 40) {
            long timeLeft = UnconsciousHandler.UNCONSCIOUS_DURATION - timePassed;
            alpha = timeLeft / 40.0f;
        } else {
            alpha = 1.0f;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        int color = (int)(alpha * 255) << 24;

        AbstractGui.fill(event.getMatrixStack(), 0, 0, width, height, color);

        RenderSystem.disableBlend();

        if (timePassed >= UnconsciousHandler.UNCONSCIOUS_DURATION) {
            nbt.putBoolean("isUnconscious", false);
        }
    }
}
*/