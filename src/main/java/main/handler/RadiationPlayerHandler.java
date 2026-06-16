package main.handler;

import main.config.ModConfig;
import main.init.ModEffects;
import main.init.utils.ModDamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RadiationPlayerHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (!player.level.isClientSide && event.phase == TickEvent.Phase.END && player.tickCount % 20 == 0) {
            CompoundNBT nbt = player.getPersistentData();
            float currentDose = nbt.getFloat("RadiationDose");

            /** Вытаскиваем значение из конфига
             * Предохраняет от сломанных цифр в конфиге
             * Доза не должна быть меньше 0.5 и больше 50.0
            */
            float fatalDose = ModConfig.FATAL_RADIATION_DOSE.get().floatValue();
            fatalDose = Math.max(0.5F, Math.min(50.0F, fatalDose));
            if (player.hasEffect(ModEffects.RADIATION.get())) {
                currentDose += 0.1F;
                if (currentDose >= fatalDose) {
                    // Высчитываем базовый урон
                    float rawDamage = 1.0F + ((currentDose - fatalDose) * 2.0F);
                    // ПРЕДОХРАНИТЕЛЬ: Урон за один тик не может превысить 10(5сердец)
                    float finalDamage = Math.min(10.0F, rawDamage);
                    player.hurt(ModDamageSources.RADIATION, finalDamage);
                    player.addEffect(new EffectInstance(Effects.CONFUSION, 400, 0));
                }
            }
            // Пассивное выведение радиации организмом(возможно я сделаю так, что это можно будет улучшать. Но не сейчас)
            if (currentDose > 0.0F) {
                    currentDose = Math.max(0.0F, currentDose - 0.002F);
                }

            // Логика антирада
                int antiradTimer = nbt.getInt("AntiradTimer");
                if (antiradTimer > 0) {
                    currentDose = Math.max(0.0F, currentDose - 0.05F);
                    nbt.putInt("AntiradTimer", antiradTimer - 20);
                }
            nbt.putFloat("RadiationDose", currentDose);
        }
    }
}
