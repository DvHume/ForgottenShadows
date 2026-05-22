package main.init;

import main.tile.AlloyFurnaceTile;
import main.tile.BatteryBoxTile;
import main.tile.ChemistryTableTile;
//import main.tile.SolarPanelTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTiles {
    public  static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "frs");

    public static final RegistryObject<TileEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = TILES.register("alloy_furnace", () -> TileEntityType.Builder.of(AlloyFurnaceTile::new, ModBlocks.ALLOY_FURNACE.get()).build(null));

    public static final RegistryObject<TileEntityType<ChemistryTableTile>> CHEMISTRY_TABLE = TILES.register("chemistry_table", () -> TileEntityType.Builder.of(ChemistryTableTile::new, ModBlocks.CHEMISTRY_TABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<BatteryBoxTile>> BATTERY_BOX = TILES.register("battery_box", () -> TileEntityType.Builder.of(BatteryBoxTile::new, ModBlocks.BATTERY_BOX.get()).build(null));

    //public static final RegistryObject<TileEntityType<SolarPanelTile>> SOLAR_PANEL = TILES.register("solar_panel", () -> TileEntityType.Builder.of(SolarPanelTile::new, ModBlocks.SOLAR_PANEL.get()).build(null));

    //public static final RegistryObject<TileEntityType<WireTile>> WIRE = TILES.register("wire", () -> TileEntityType.Builder.of(WireTile::new, ModBlocks.WIRE.get()).build(null));
}
