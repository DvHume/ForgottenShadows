package main.block;

import main.init.utils.ModDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class FerroxAcidBlock extends FlowingFluidBlock {

    public FerroxAcidBlock(java.util.function.Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.hurt(ModDamageSources.FERROX_ACID, 16.0f);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        BlockPos below = pos.below();
        BlockState belowState = world.getBlockState(below);

        if (!belowState.isAir() && belowState.getBlock() != this && below.getY() >= 0) {
            world.destroyBlock(below, false);
            world.setBlock(below, this.defaultBlockState(), 3);
        }
    }
}
