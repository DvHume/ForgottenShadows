package main.handler;

import main.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
@EventBusSubscriber
public class HeavyWoundHandler {
    private static final float MAX_HEALTH_WITH_WOUND = 10.0f;

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!entity.hasEffect(ModEffects.HEAVY_WOUND.get())) {
            return;
        }
        float currentHealth = entity.getHealth();
        float healAmount = event.getAmount();

        if (currentHealth >= MAX_HEALTH_WITH_WOUND) {
            event.setCanceled(true);
            return;
        }
        if (currentHealth + healAmount > MAX_HEALTH_WITH_WOUND) {
            event.setAmount(MAX_HEALTH_WITH_WOUND - currentHealth);
        }
    }
}
