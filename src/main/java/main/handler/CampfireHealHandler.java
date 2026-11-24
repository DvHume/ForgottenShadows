package main.handler;

import main.init.ModEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
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
    private static final int SCAN_Y = 1; // костёр на уровне ног или +-1
    private static final int SCAN_INTERVAL = 20;  // полная перепроверка раз в секунду
    private static final int INITIAL_DELAY = 100;
    private static final int HEAL_INTERVAL = 80;
    private static final int REST_TIME = 1200;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.player;
        CompoundNBT nbt = player.getPersistentData();

        if (player.hasEffect(ModEffects.GOOD_REST.get()) && player.tickCount % 20 == 0) {
            player.getFoodData().addExhaustion(-0.4f);
        }

        boolean nearCampfire = isNearCampfire(player, nbt);

        if (!nearCampfire) {
            nbt.putInt("campfireTime", 0);
            nbt.putInt("campfireDelay", 0);
            return;
        }

        int campfireTime = nbt.getInt("campfireTime") + 1;
        nbt.putInt("campfireTime", campfireTime);

        if (campfireTime < INITIAL_DELAY) return;

        // Снижение Heavy Wound на 1 уровень раз в HEAL_INTERVAL
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

        // эффект после минуты у костра
        if (campfireTime >= REST_TIME) {
            if (!player.hasEffect(ModEffects.GOOD_REST.get())) {
                player.addEffect(new EffectInstance(
                        ModEffects.GOOD_REST.get(),
                        6000, // 5 минут
                        0,
                        false, false
                ));
            }
            nbt.putInt("campfireTime", 0);
        }
    }

    // Кэшированная проверка, сканируем блоки только при движении
    // или раз в SCAN_INTERVAL тиков
    private static boolean isNearCampfire(ServerPlayerEntity player, CompoundNBT nbt) {
        int now = player.tickCount;
        BlockPos pos = player.blockPosition();

        boolean moved = nbt.getLong("campfireLastPos") != pos.asLong();
        boolean due = now - nbt.getInt("campfireLastScan") >= SCAN_INTERVAL;

        if (!moved && !due) {
            return nbt.getBoolean("campfireNear"); // ответ из кэша
        }

        boolean found = scanForCampfire(player.level, pos);

        nbt.putLong("campfireLastPos", pos.asLong());
        nbt.putInt("campfireLastScan", now);
        nbt.putBoolean("campfireNear", found);
        return found;
    }

    // Само сканирование: куб 7x3x7 вокруг ног игрока
    private static boolean scanForCampfire(World world, BlockPos center) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -SCAN_Y; y <= SCAN_Y; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockState state = world.getBlockState(center);
                    if (world.getBlockState(center.offset(x, y, z)).getBlock() == Blocks.CAMPFIRE && state.getValue(CampfireBlock.LIT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}