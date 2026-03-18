package main.init;

import main.tile.AlloyFurnaceTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTiles {
    public  static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "frs");

    public static final RegistryObject<TileEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = TILES.register("alloy_furnace", () -> TileEntityType.Builder.of(AlloyFurnaceTile::new, ModBlocks.ALLOY_FURNACE.get()).build(null));
}
