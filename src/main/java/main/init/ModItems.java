package main.init;

import main.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.item.BlockItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "frs");
    public static final RegistryObject<Item> DIVINE_METAL = ITEMS.register("divine_metal", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> DIVINE_SWORD = ITEMS.register("divine_sword", () -> new DivineSword());

    public static final RegistryObject<Item> HERBAL_BANDAGE = ITEMS.register("herbal_bandage", () -> new HerbalBandage());

    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore", () -> new BlockItem(ModBlocks.TIN_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));
     //TIN - я забыл добавить ingot извините за путаницу
    public static final RegistryObject<Item> TIN = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> FLAMING_PICKAXE = ITEMS.register("flaming_pickaxe", () -> new FlamingPickaxe());

    public static final RegistryObject<Item> FLAMING_INGOT = ITEMS.register("flaming_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> DARK_STEEL_INGOT = ITEMS.register("dark_steel_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> DARK_STEEL_SPEAR = ITEMS.register("dark_steel_spear", () -> new DarkSteelSpear());

    public static final RegistryObject<Item> IS_CULT = ITEMS.register("is_cult", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VIRTON_SHARD = ITEMS.register("virton_shard", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> VIRTON_CRYSTAL = ITEMS.register("virton_crystal", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> METEOR_IRON_ORE = ITEMS.register("meteor_iron_ore", () -> new BlockItem(ModBlocks.METEOR_IRON_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_GOLD_ORE = ITEMS.register("meteor_gold_ore", () -> new BlockItem(ModBlocks.METEOR_GOLD_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> IRIDIUM_ORE = ITEMS.register("iridium_ore", () -> new BlockItem(ModBlocks.IRIDIUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> OSMIUM_ORE = ITEMS.register("osmium_ore", () -> new BlockItem(ModBlocks.OSMIUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> PLATINUM_ORE = ITEMS.register("platinum_ore", () -> new BlockItem(ModBlocks.PLATINUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_ROCK = ITEMS.register("meteor_rock", () -> new BlockItem(ModBlocks.METEOR_ROCK.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_MAGMA = ITEMS.register("meteor_magma", () -> new BlockItem(ModBlocks.METEOR_MAGMA.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> CLOTH = ITEMS.register("cloth", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> DISINFECTED_CLOTH = ITEMS.register("disinfected_cloth", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> HERBAL_SOLUTION = ITEMS.register("herbal_solution", () -> new HerbalSolution());

    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> IRIDIUM_INGOT = ITEMS.register("iridium_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> OSMIUM_INGOT = ITEMS.register("osmium_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> SHEPHERDS_PURSE = ITEMS.register("shepherds_purse", () -> new BlockItem(ModBlocks.SHEPHERDS_PURSE.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> FLAMING_SWORD = ITEMS.register("flaming_sword", () -> new FlamingSword());

    public static final RegistryObject<Item> ALLOY_FURNACE = ITEMS.register("alloy_furnace", () -> new BlockItem(ModBlocks.ALLOY_FURNACE.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    public static final RegistryObject<Item> NICKEL_ORE = ITEMS.register("nickel_ore", () -> new BlockItem(ModBlocks.NICKEL_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> LEAFY_STEM = ITEMS.register("leafy_stem", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> ROTTEN_EGG = ITEMS.register("rotten_egg", () -> new RottenEgg());

    public static final RegistryObject<Item> BLOOD_QUARTZ_ORE = ITEMS.register("blood_quartz_ore", () -> new BlockItem(ModBlocks.BLOOD_QUARTZ_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> FERROX_ORE = ITEMS.register("ferrox_ore", () -> new BlockItem(ModBlocks.FERROX_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> BLOOD_QUARTZ = ITEMS.register("blood_quartz", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> FERROX_POWDER = ITEMS.register("ferrox_powder", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> FERROX_INGOT = ITEMS.register("ferrox_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> OBSIDIAN_POWDER = ITEMS.register("obsidian_powder", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> FLAMING_ORE = ITEMS.register("flaming_ore", () -> new BlockItem(ModBlocks.FLAMING_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> ASH_LOG = ITEMS.register("ash_log", () -> new BlockItem(ModBlocks.ASH_LOG.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));
}
