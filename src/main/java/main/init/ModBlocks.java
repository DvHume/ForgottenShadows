package main.init;

import main.block.AlloyFurnace;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "frs");

    public static final RegistryObject<Block> ALLOY_FURNACE = BLOCKS.register("alloy_furnace", () -> new AlloyFurnace(AbstractBlock.Properties.of(Material.STONE).strength(3.5f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(3.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> METEOR_IRON_ORE = BLOCKS.register("meteor_iron_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(3.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> METEOR_GOLD_ORE = BLOCKS.register("meteor_gold_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(3.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> IRIDIUM_ORE = BLOCKS.register("iridium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(4.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> OSMIUM_ORE = BLOCKS.register("osmium_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(4.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> PLATINUM_ORE = BLOCKS.register("platinum_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(5.0f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> METEOR_ROCK = BLOCKS.register("meteor_rock", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(3.5f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> METEOR_MAGMA = BLOCKS.register("meteor_magma", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(4.2f).requiresCorrectToolForDrops()));
}
