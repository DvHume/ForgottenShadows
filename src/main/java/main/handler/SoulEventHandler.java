package main.handler;

import main.capability.SoulCapability;
import main.capability.SoulHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;

@Mod.EventBusSubscriber
public class SoulEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof PlayerEntity)) return;
        event.addCapability(SoulCapability.ID, new ICapabilityProvider() {
            private final SoulHandler handler = new SoulHandler();
            private final LazyOptional<SoulHandler> optional = LazyOptional.of(() -> handler);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == SoulCapability.SOUL_CAP) {
                    return optional.cast();
                }
                return LazyOptional.empty();
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        PlayerEntity original = event.getOriginal();
        PlayerEntity clone = event.getPlayer();


        original.getCapability(SoulCapability.SOUL_CAP).ifPresent(oldSouls -> {clone.getCapability(SoulCapability.SOUL_CAP).ifPresent(newSouls -> {newSouls.setSouls(oldSouls.getSouls());});});
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level.isClientSide) return;

        PlayerEntity player = event.player;

        player.getCapability(SoulCapability.SOUL_CAP).ifPresent(souls -> {
            float currentSouls = souls.getSouls();

            //if the soul is in minus
            if (currentSouls < 0) {
                //Constant weakness if the number of points is in the minus
                if (!player.hasEffect(Effects.WEAKNESS)) {
                    player.addEffect(new EffectInstance(Effects.WEAKNESS, 60, 0, false, false));
                }

                //Sun damage every 40 ticks (2 sec)
                //canSeeSky — open player air
                // isDay — daytime now
                if (player.level.canSeeSky(player.blockPosition())
                        && player.level.isDay()
                        && player.tickCount % 40 == 0) {
                    player.hurt(DamageSource.ON_FIRE, 1.0f);
                }

                //Applies slowness starting at -20, then every - 10 after that
                // (int)(Math.abs(currentSouls) - 20) / 10 — count how many thresholds have been hit
                if (currentSouls <= -20) {
                    int slowLevel = (int)(Math.abs(currentSouls) - 20) / 10;
                    EffectInstance current = player.getEffect(Effects.MOVEMENT_SLOWDOWN);
                    boolean needsUpdate = current == null || current.getAmplifier() != slowLevel;
                    if (needsUpdate) {
                        player.addEffect(new EffectInstance(
                                Effects.MOVEMENT_SLOWDOWN,
                                3600, //3 min
                                Math.min(slowLevel, 5), //max 6 lvl
                                false, false
                        ));
                    }
                }
            }
        });
    }
}
