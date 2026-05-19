package main.init;

import main.container.AlloyFurnaceContainer;
import main.container.ChemistryTableContainer;
import main.tile.AlloyFurnaceTile;
import main.tile.ChemistryTableTile;
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

    public static final RegistryObject<ContainerType<ChemistryTableContainer>> CHEMISTRY_TABLE = CONTAINERS.register("chemistry_table", () -> IForgeContainerType.create((id, playerInventory, data) -> {
        net.minecraft.util.math.BlockPos pos = data.readBlockPos();
        ChemistryTableTile tile = (ChemistryTableTile) playerInventory.player.level.getBlockEntity(pos);
        return new ChemistryTableContainer(id, playerInventory, tile, tile.dataAccess);
    }));
}
