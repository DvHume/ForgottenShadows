package main.handler;

import main.init.ModEffects;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CampfireHealHandler {

    private static final int RADIUS = 3;
    private static final int INITIAL_DELAY = 100;
    private static final int HEAL_INTERVAL = 80;
    private static final int REST_TIME = 1200;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.player;
        CompoundNBT nbt = player.getPersistentData();

        boolean nearCampfire = isNearCampfire(player);

        if (!nearCampfire) {
            //Reset the timer if the player moves away from the fire
            nbt.putInt("campfireTime", 0);
            nbt.putInt("campfireDelay", 0);
            return;
        }

        //Increase the time spent near the fire
        int campfireTime = nbt.getInt("campfireTime") + 1;
        nbt.putInt("campfireTime", campfireTime);

        //Initial delay
        if (campfireTime < INITIAL_DELAY) return;

        //Reduces Heavy wound by 1lvl
        if (player.hasEffect(ModEffects.HEAVY_WOUND.get())) {
            int delay = nbt.getInt("campfireDelay") + 1;
            nbt.putInt("campfireDelay", delay);

            if (delay >= HEAL_INTERVAL) {
                nbt.putInt("campfireDelay", 0);

                EffectInstance current = player.getEffect(ModEffects.HEAVY_WOUND.get());
                int currentAmplifier = current.getAmplifier();
                int currentDuration = current.getDuration();

                player.removeEffect(ModEffects.HEAVY_WOUND.get());

                if (currentAmplifier > 0) {
                    player.addEffect(new EffectInstance(
                            ModEffects.HEAVY_WOUND.get(),
                            currentDuration,
                            currentAmplifier - 1,
                            false, false
                    ));
                }
            }
        }

        //GOOD REST effect after 1 minute
        if (campfireTime >= REST_TIME) {
            if (!player.hasEffect(ModEffects.GOOD_REST.get())) {
                player.addEffect(new EffectInstance(
                        ModEffects.GOOD_REST.get(),
                        6000, //5 minute
                        0,
                        false, false
                ));
            }
            //Resets to avoid spam
            nbt.putInt("campfireTime", 0);
        }

        if (player.hasEffect(ModEffects.GOOD_REST.get())) {
            if (player.tickCount % 20 == 0) {
                player.getFoodData().addExhaustion(-0.2f);
            }
        }
    }

    private static boolean isNearCampfire(PlayerEntity player) {
        World world = player.level;
        BlockPos playerPos = player.blockPosition();

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    //Check that the fire is only a regular one
                    if (world.getBlockState(pos).getBlock() == Blocks.CAMPFIRE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}