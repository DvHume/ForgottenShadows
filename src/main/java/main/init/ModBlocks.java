package main.init;

import main.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "frs");

    // ============================================================
    // ====== ORES ======
    // ============================================================
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> METEOR_IRON_ORE = BLOCKS.register("meteor_iron_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> METEOR_GOLD_ORE = BLOCKS.register("meteor_gold_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> IRIDIUM_ORE = BLOCKS.register("iridium_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(4.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> OSMIUM_ORE = BLOCKS.register("osmium_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(4.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PLATINUM_ORE = BLOCKS.register("platinum_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(5.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BLOOD_QUARTZ_ORE = BLOCKS.register("blood_quartz_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .sound(SoundType.NETHERRACK)));

    public static final RegistryObject<Block> FERROX_ORE = BLOCKS.register("ferrox_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.1f)));

    public static final RegistryObject<Block> SULFUR_ORE = BLOCKS.register("sulfur_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore",
            () -> new UraniumOreBlock());

    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> FLAMING_ORE = BLOCKS.register("flaming_ore",
            () -> new FlamingOre());

    // ============================================================
    // ====== BLOCKS ======
    // ============================================================
    public static final RegistryObject<Block> ALLOY_FURNACE = BLOCKS.register("alloy_furnace",
            () -> new AlloyFurnace(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CHEMISTRY_TABLE = BLOCKS.register("chemistry_table",
            () -> new ChemistryTable(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BATTERY_BOX = BLOCKS.register("battery_box",
            () -> new BatteryBox(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SOLAR_PANEL = BLOCKS.register("solar_panel",
            () -> new SolarPanelBlock());

    public static final RegistryObject<Block> DARK_STEEL_BLOCK = BLOCKS.register("dark_steel_block",
            () -> new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(5.1f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block",
            () -> new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RADIOACTIVE_BLOCK = BLOCKS.register("radioactive_block",
            () -> new RadioactiveScorchedBlock());

    // ============================================================
    // ====== METEORS ======
    // ============================================================
    public static final RegistryObject<Block> METEOR_ROCK = BLOCKS.register("meteor_rock",
            () -> new MeteorRockBlock());

    public static final RegistryObject<Block> METEOR_MAGMA = BLOCKS.register("meteor_magma",
            () -> new MeteorMagmaBlock());

    // ============================================================
    // ====== F ======
    // ============================================================
    public static final RegistryObject<Block> SHEPHERDS_PURSE = BLOCKS.register("shepherds_purse",
            () -> new ShepherdsPurse());

    // ============================================================
    // ====== WOOD ======
    // ============================================================
    public static final RegistryObject<Block> ASH_LOG = BLOCKS.register("ash_log",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD)
                    .strength(2.0f)));

    // ============================================================
    // ====== FLUIDS ======
    // ============================================================
    public static final RegistryObject<FlowingFluidBlock> FERROX_ACID_BLOCK = BLOCKS.register("ferrox_acid",
            () -> new FerroxAcidBlock(ModFluids.FERROX_ACID,
                    AbstractBlock.Properties.of(Material.WATER)
                            .noCollission()
                            .strength(100.0f)
                            .noDrops()
                            .randomTicks()));
}