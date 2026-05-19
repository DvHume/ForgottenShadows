/*package main.effect;

import main.capability.SoulCapability;
import main.init.ModNetwork;
import main.network.SoulSyncPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class CurseSoulEffect extends Effect {

    public CurseSoulEffect() {
        super(EffectType.HARMFUL, 0x4B0082);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(DamageSource.MAGIC, 1.0f);

        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
                souls.addSouls(-3.0f);

                ModNetwork.CHANNEL.sendTo(
                        new SoulSyncPacket(souls.getSouls()),
                        player.connection.connection,
                        net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT
                );
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }
}*/