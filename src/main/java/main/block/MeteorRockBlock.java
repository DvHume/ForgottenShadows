package main.block;

import main.init.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class MeteorRockBlock extends Block {

    public static final BooleanProperty HOT = BooleanProperty.create("hot");

    public MeteorRockBlock() {
        super(Properties.of(Material.STONE)
                .strength(4.0f)
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HOT, true));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HOT);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(HOT)) {
            world.getBlockTicks().scheduleTick(pos, this, 2000 + world.random.nextInt(400));
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (state.getValue(HOT)) {
            world.setBlock(pos, state.setValue(HOT, false), 3);
        }
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (!world.isClientSide) {
            if (world.getBlockState(pos).getValue(HOT)) {
                entity.hurt(DamageSource.HOT_FLOOR, 2.0f);
            }
        }
        super.stepOn(world, pos, entity);
    }
}
