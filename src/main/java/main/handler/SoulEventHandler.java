package main.handler;

import main.capability.SoulCapability;
import main.capability.SoulHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
}
