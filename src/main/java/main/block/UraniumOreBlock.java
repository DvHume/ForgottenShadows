package main.block;

import main.init.ModEffects;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UraniumOreBlock extends Block {
    public UraniumOreBlock() {
        super(AbstractBlock.Properties.of(Material.STONE).strength(3.0f));
    }

    @Override
    public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, TileEntity tile, ItemStack stack) {
        super.playerDestroy(world, player, pos, state, tile, stack);

        if (!world.isClientSide) {
            CompoundNBT nbt = player.getPersistentData();
            nbt.putFloat("RadiationDose", nbt.getFloat("RadiationDose") + 0.8F);
            player.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 400, 0));

            AreaEffectCloudEntity radonGas = new AreaEffectCloudEntity(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d);
            radonGas.setRadius(2.5f);
            radonGas.setRadiusPerTick(-0.002f);
            radonGas.setDuration(600);
            radonGas.setParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT);
            radonGas.setFixedColor(0);
            radonGas.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 300, 0));
            world.addFreshEntity(radonGas);
        }
    }
}
