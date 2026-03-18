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
}
