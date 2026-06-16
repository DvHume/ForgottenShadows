package main.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RadioactiveScorchedBlock extends Block {

    public static final EnumProperty<RadioactiveType> TYPE = EnumProperty.create("type", RadioactiveType.class);

    public RadioactiveScorchedBlock() {
        super(AbstractBlock.Properties.of(Material.DIRT).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, RadioactiveType.GRASS));
    }

    @Override
    public SoundType getSoundType(BlockState state) {
        RadioactiveType type = state.getValue(TYPE);
        switch (type) {
            case SAND:
                return SoundType.SAND;
            case GRAVEL:
                return SoundType.GRAVEL;
            case GRASS:
            default:
                return SoundType.GRASS;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    /*@Override
    public IFormattableTextComponent getName() {
        RadioactiveType type = this.defaultBlockState().getValue(TYPE);
        return new TranslationTextComponent(this.getDescriptionId() + "." + type.getSerializedName());
    }*/
}
