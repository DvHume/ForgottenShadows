package main.init;

import main.fluid.FerroxAcid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "frs");

    public static final RegistryObject<FlowingFluid> FERROX_ACID = FLUIDS.register("ferrox_acid", () -> new ForgeFlowingFluid.Source(FerroxAcid.PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERROX_ACID_FLOWING = FLUIDS.register("ferrox_acid_flowing", () -> new ForgeFlowingFluid.Flowing(FerroxAcid.PROPERTIES));
}
