/*package main.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SoulCapability {
    @CapabilityInject(ISoulHandler.class)
    public static Capability<ISoulHandler> SOUL_CAP = null;
    public static final ResourceLocation ID = new ResourceLocation("frs", "souls");

    //capability registration
    public static void register() {
        CapabilityManager.INSTANCE.register(
                ISoulHandler.class,
                new Capability.IStorage<ISoulHandler>() {
                    @Override
                    public CompoundNBT writeNBT(Capability<ISoulHandler> capability, ISoulHandler instance, net.minecraft.util.Direction side) {
                        return ((SoulHandler) instance).serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<ISoulHandler> capability,
                                        ISoulHandler instance, net.minecraft.util.Direction side, net.minecraft.nbt.INBT nbt) {
                        ((SoulHandler) instance).deserializeNBT((CompoundNBT) nbt);
                    }
                },
                SoulHandler::new
        );
    }
}
*/