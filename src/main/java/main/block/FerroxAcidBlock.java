package main.block;

import main.init.utils.ModDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public class FerroxAcidBlock extends FlowingFluidBlock {

    public FerroxAcidBlock(java.util.function.Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            entity.makeStuckInBlock(state, new Vector3d(0.1d, 0.04d, 0.1d));

            Vector3d motion = entity.getDeltaMovement();
            if (motion.y > 0) {
                entity.setDeltaMovement(motion.x, motion.y * 0.2d, motion.z);
            }

            if (world.getGameTime() % 10 == 0) {
                livingEntity.hurt(ModDamageSources.FERROX_ACID, 10.0f);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        this.tryDissolveNeighbors(world, pos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        this.tryDissolveNeighbors(world, pos);
    }

    private void tryDissolveNeighbors(World world, BlockPos pos) {
        if (world.isClientSide) return;

        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.relative(direction);
            BlockState targetState = world.getBlockState(targetPos);

            if (!targetState.isAir()
            && targetState.getBlock() != Blocks.BEDROCK
            && targetState.getBlock() != this
            && targetState.getFluidState().isEmpty()) {

                if (targetState.getDestroySpeed(world, targetPos) <= 5.0f) {
                    world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }
}
