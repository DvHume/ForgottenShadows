package main.handler;

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
                        int radius = 4;
                        for (int x = -radius; x <= radius; x++) {
                            for (int z = -radius; z <= radius; z++) {
                                BlockPos targetPos = pos.offset(x, -1, z);
                                BlockState state = world.getBlockState(targetPos);
                                if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.MYCELIUM.defaultBlockState());
                                }
                            }
                        }
                        AreaEffectCloudEntity localRadiation = new AreaEffectCloudEntity(
                                world, pos.getX(), pos.getY(), pos.getZ()
                        );
                        localRadiation.setRadius(2.0F);
                        localRadiation.setDuration(120);
                        localRadiation.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 200, 0));
                        world.addFreshEntity(localRadiation);
                    }
                }
            }
        }
    }
}
