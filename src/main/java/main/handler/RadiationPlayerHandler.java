package main.handler;

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

            if (player.hasEffect(ModEffects.RADIATION.get())) {
                currentDose += 0.1F;
                if (currentDose >= 4.0F) {
                    float finalDamage = 1.0F + ((currentDose - 4.0F) * 2.0F);
                    player.hurt(ModDamageSources.RADIATION, finalDamage);
                    player.addEffect(new EffectInstance(Effects.CONFUSION, 400, 0));
                }
            }
                if (currentDose > 0.0F) {
                    currentDose = Math.max(0.0F, currentDose - 0.002F);
                }

            nbt.putFloat("RadiationDose", currentDose);
        }
        CompoundNBT nbt = player.getPersistentData();
        int antiradTimer = nbt.getInt("AntiradTimer");
        if (antiradTimer > 0) {
            float currentDose = nbt.getFloat("RadiationDose");
            currentDose = Math.max(0.0F, currentDose - 0.766F);
            nbt.putFloat("RadiationDose", currentDose);
            nbt.putInt("AntiradTimer", antiradTimer - 20);
        }
    }
}
