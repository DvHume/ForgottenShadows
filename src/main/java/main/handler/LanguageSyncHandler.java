package main.handler;

import main.init.ModNetwork;
import main.network.LanguageSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class LanguageSyncHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof net.minecraft.entity.player.PlayerEntity)) return;
        if (!event.getWorld().isClientSide) return;

        String lang = Minecraft.getInstance()
                .getLanguageManager().getSelected().getCode();

        ModNetwork.CHANNEL.sendToServer(new LanguageSyncPacket(lang));
    }
}
