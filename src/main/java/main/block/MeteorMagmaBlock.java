package main.block;

import main.init.ModBlocks;
import main.world.MeteorCraterGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MeteorMagmaBlock extends Block {
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

    public MeteorMagmaBlock() {
        super(Properties.of(Material.STONE)
                .strength(5.0f)
                .requiresCorrectToolForDrops()
                .lightLevel(state -> 9));
        this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, false));
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && entity instanceof net.minecraft.entity.LivingEntity) {
            entity.hurt(DamageSource.HOT_FLOOR, 3.0f);
        }
        super.stepOn(world, pos, entity);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(NATURAL)) {
            world.getBlockTicks().scheduleTick(pos, this, 1000 + world.random.nextInt(400));
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld world,BlockPos pos,Random rand) {
        if (!state.getValue(NATURAL)) return;
        BlockState hotRock = ModBlocks.METEOR_ROCK.get()
                    .defaultBlockState()
                    .setValue(MeteorRockBlock.HOT, true);
        world.setBlock(pos, hotRock, 3);

        if (rand.nextFloat() < 0.4f) {
            world.setBlock(pos, MeteorCraterGenerator.getRandomOre(rand), 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NATURAL);
    }

    @Override
    public void animateTick(BlockState state, World world,BlockPos pos, Random rand) {
        if (rand.nextInt(3) == 0) {
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
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
