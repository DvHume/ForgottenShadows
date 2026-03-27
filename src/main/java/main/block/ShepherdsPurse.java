package main.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ShepherdsPurse extends BushBlock {

    private static final VoxelShape SHAPE =
            Block.box(2, 0, 2, 14, 12, 14);

    public ShepherdsPurse() {
        super(Properties.copy(Blocks.GRASS)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }
}
