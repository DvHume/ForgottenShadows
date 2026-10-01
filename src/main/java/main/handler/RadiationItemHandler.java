package main.handler;

import main.block.RadioactiveScorchedBlock;
import main.block.RadioactiveType;
import main.init.ModBlocks;
import main.init.ModEffects;
import main.item.other.RadioactiveItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RadiationItemHandler {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.world instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.world;
            for (Entity entity : world.getAllEntities()) {
                if (entity instanceof ItemEntity) {
                    ItemEntity itemEntity = (ItemEntity) entity;

                    if (itemEntity.getItem().getItem() instanceof RadioactiveItem && itemEntity.tickCount % 60 == 0) {
                        BlockPos pos = itemEntity.blockPosition();
                        RadioactiveItem radItem = (RadioactiveItem) itemEntity.getItem().getItem();
                        float power = radItem.getRadiationPerSec();
                        int radius = Math.max(1, Math.round(power * 60.0F));
                        if (radius > 10) radius = 10;

                        for (int x = -radius; x <= radius; x++) {
                            for (int z = -radius; z <= radius; z++) {
                                BlockPos targetPos = pos.offset(x, -1, z);
                                if ((x * x + z * z) <= (radius * radius)) {
                                    double distancePercent = Math.sqrt(x * x + z * z) / radius;
                                    if (world.random.nextDouble() > (distancePercent * 0.5D)) {
                                        BlockState state = world.getBlockState(targetPos);
                                        BlockState radioactiveState = null;
                                        if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)
                                                || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM)) {
                                            radioactiveState = ModBlocks.RADIOACTIVE_BLOCK.get().defaultBlockState().setValue(RadioactiveScorchedBlock.TYPE, RadioactiveType.GRASS);
                                        } else if (state.is(Blocks.SAND) || state.is(Blocks.RED_SAND)) {
                                            radioactiveState = ModBlocks.RADIOACTIVE_BLOCK.get().defaultBlockState().setValue(RadioactiveScorchedBlock.TYPE, RadioactiveType.SAND);
                                        } else if (state.is(Blocks.GRAVEL)) {
                                            radioactiveState = ModBlocks.RADIOACTIVE_BLOCK.get().defaultBlockState().setValue(RadioactiveScorchedBlock.TYPE, RadioactiveType.GRAVEL);
                                        }
                                        if (radioactiveState != null) {
                                            world.setBlockAndUpdate(targetPos, radioactiveState);
                                        }
                                    }
                                }
                            }
                        }

                        AreaEffectCloudEntity localRadiation = new AreaEffectCloudEntity(
                                world, pos.getX(), pos.getY(), pos.getZ()
                        );
                        float cloudRadius = Math.max(1.5F, radius * 0.6F);
                        localRadiation.setRadius(cloudRadius);
                        localRadiation.setDuration(120);
                        localRadiation.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 200, 0));
                        // Флаг для оптимизации поиска гейгером:
                        localRadiation.getPersistentData().putBoolean("RadiationCloud", true);
                        world.addFreshEntity(localRadiation);
                    }
                }
            }
        }
    }
}
