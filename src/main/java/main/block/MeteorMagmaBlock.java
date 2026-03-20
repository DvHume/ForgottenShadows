package main.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MeteorMagmaBlock extends Block {

    public MeteorMagmaBlock() {
        super(Properties.of(Material.STONE)
                .strength(5.0f)
                .requiresCorrectToolForDrops()
                .lightLevel(state -> 9));
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        entity.hurt(DamageSource.HOT_FLOOR, 3.0f);
        super.stepOn(world, pos, entity);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextInt(3) == 0) {
            world.addParticle(ParticleTypes.LAVA,
                    pos.getX() + rand.nextDouble(),
                    pos.getY() + 1.0,
                    pos.getZ() + rand.nextDouble(), 0, 0.05, 0);
        }
        world.addParticle(ParticleTypes.SMOKE,
                pos.getX() + rand.nextDouble(),
                pos.getY() + 1.0,
                pos.getZ() + rand.nextDouble(), 0, 0.03, 0);
    }
}
