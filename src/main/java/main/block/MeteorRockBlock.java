package main.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


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
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (world.getBlockState(pos).getValue(HOT)) {
            entity.hurt(DamageSource.HOT_FLOOR, 2.0f);
        }
        super.stepOn(world, pos, entity);
    }
}
