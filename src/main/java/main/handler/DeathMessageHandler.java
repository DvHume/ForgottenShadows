package main.handler;

import main.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DeathMessageHandler {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        if (event.getEntityLiving().level.isClientSide) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();

        if (player.hasEffect(ModEffects.BLEEDING.get())) {
            player.level.players().forEach(p -> {
                p.sendMessage(
                        new TranslationTextComponent(
                                "death.frs.bleeding",
                                player.getDisplayName()
                        ),
                        player.getUUID()
                );
            });
        }
    }
}
