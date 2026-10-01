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

            if (player.isCreative() || player.isSpectator()) return;

            CompoundNBT nbt = player.getPersistentData();
            float currentDose = nbt.getFloat("RadiationDose");

            // Читаем смертельный порог из конфига (по умолчанию 6.0)
            float fatalDose = ModConfig.FATAL_RADIATION_DOSE.get().floatValue();
            fatalDose = Math.max(0.5F, Math.min(50.0F, fatalDose));

            // Урон при превышении смертельной дозы
            if (currentDose >= fatalDose) {
                float rawDamage = 1.0F + ((currentDose - fatalDose) * 2.0F);
                float finalDamage = Math.min(10.0F, rawDamage);
                player.hurt(ModDamageSources.RADIATION, finalDamage);
                player.addEffect(new EffectInstance(Effects.CONFUSION, 400, 0));
            }

            boolean underRadiation = player.hasEffect(ModEffects.RADIATION.get());
            boolean underAntirad = player.hasEffect(ModEffects.ANTIRAD.get());

            // Пассивное очищение организма
            // Радиация выводится если активен антирад ИЛИ если рядом нет радиоактивного источника
            if (underAntirad || (!underRadiation && currentDose > 0.0F)) {
                float reduction = underAntirad ? 0.05F : 0.005F;
                currentDose = Math.max(0.0F, currentDose - reduction);
                nbt.putFloat("RadiationDose", currentDose);
            }
        }
    }
}
