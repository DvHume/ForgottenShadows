package main.fluid;

import main.init.ModBlocks;
import main.init.ModFluids;
import main.init.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class FerroxAcid {
    private static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    private static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");
    //private static final ResourceLocation OVERLAY_RL = new ResourceLocation("");
    public static final int ACID_COLOR = 0xFF03FCE3;

    public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERROX_ACID,
            ModFluids.FERROX_ACID_FLOWING,
            FluidAttributes.builder(WATER_STILL, WATER_FLOWING)
                    //.overlay()
                    .color(ACID_COLOR)
                    .density(3000)
                    .viscosity(6000)
    )
            .slopeFindDistance(4)
            .levelDecreasePerBlock(1)
            .tickRate(5)
            .block(ModBlocks.FERROX_ACID_BLOCK)
            .bucket(ModItems.FERROX_ACID_BUCKET);
}
