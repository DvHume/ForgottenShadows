package main.handler;

import main.init.ModBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AcidEntityHandler {

    @SubscribeEvent
    public static void onEntityInAcid(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.level;

        if (world.getBlockState(entity.blockPosition()).getBlock() == ModBlocks.FERROX_ACID_BLOCK.get()) {

            if (!world.isClientSide && world.random.nextInt(10) == 0 && entity.getDeltaMovement().lengthSqr() > 0.005) {
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.4f, 1.2f);
            }
            // Bad style
            if (world.isClientSide && entity.getDeltaMovement().lengthSqr() > 0.01) {
                for (int i = 0; i < 3; i++) {
                    world.addParticle(ParticleTypes.SNEEZE,
                            entity.getRandomX(0.4d),
                            entity.getY() + 0.1d,
                            entity.getRandomZ(0.4d),
                            0.0d, 0.1d, 0.0d);
                }
            }
        }
    }
}
