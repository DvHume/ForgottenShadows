package main.init;

import main.container.AlloyFurnaceContainer;
import main.tile.AlloyFurnaceTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, "frs");

    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE = CONTAINERS.register("alloy_furnace", () -> IForgeContainerType.create((id, playerInventory, data) -> {
        net.minecraft.util.math.BlockPos pos = data.readBlockPos();
        AlloyFurnaceTile tile = (AlloyFurnaceTile) playerInventory.player.level.getBlockEntity(pos);
        return new AlloyFurnaceContainer(id, playerInventory, tile);
    }));
}
