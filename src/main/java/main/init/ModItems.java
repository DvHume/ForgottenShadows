package main.init;

import main.item.*;
import main.item.Tools.FlamingPickaxe;
import main.item.Tools.GeigerCounterItem;
import main.item.battery.LargeBatteryItem;
import main.item.battery.RedstoneBatteryItem;
import main.item.battery.SuperBatteryItem;
import main.item.other.HerbalSolution;
import main.item.other.MugBeer;
import main.item.other.RadioactiveItem;
import main.item.other.Wine;
import main.item.weapon.DarkSteelSpear;
import main.item.weapon.DivineSword;
import main.item.weapon.FlamingSword;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "frs");
    public static final RegistryObject<Item> DIVINE_METAL = ITEMS.register("divine_metal", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> DIVINE_SWORD = ITEMS.register("divine_sword", () -> new DivineSword());

    public static final RegistryObject<Item> HERBAL_BANDAGE = ITEMS.register("herbal_bandage", () -> new HerbalBandage());

    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore", () -> new BlockItem(ModBlocks.TIN_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));
     //TIN - я забыл добавить ingot извините за путаницу
    public static final RegistryObject<Item> TIN = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> FLAMING_PICKAXE = ITEMS.register("flaming_pickaxe", () -> new FlamingPickaxe());

    public static final RegistryObject<Item> FLAMING_INGOT = ITEMS.register("flaming_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> DARK_STEEL_INGOT = ITEMS.register("dark_steel_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> DARK_STEEL_SPEAR = ITEMS.register("dark_steel_spear", () -> new DarkSteelSpear());

    public static final RegistryObject<Item> VIRTON_SHARD = ITEMS.register("virton_shard", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> VIRTON_CRYSTAL = ITEMS.register("virton_crystal", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> METEOR_IRON_ORE = ITEMS.register("meteor_iron_ore", () -> new BlockItem(ModBlocks.METEOR_IRON_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_GOLD_ORE = ITEMS.register("meteor_gold_ore", () -> new BlockItem(ModBlocks.METEOR_GOLD_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> IRIDIUM_ORE = ITEMS.register("iridium_ore", () -> new BlockItem(ModBlocks.IRIDIUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> OSMIUM_ORE = ITEMS.register("osmium_ore", () -> new BlockItem(ModBlocks.OSMIUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> PLATINUM_ORE = ITEMS.register("platinum_ore", () -> new BlockItem(ModBlocks.PLATINUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_ROCK = ITEMS.register("meteor_rock", () -> new BlockItem(ModBlocks.METEOR_ROCK.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> METEOR_MAGMA = ITEMS.register("meteor_magma", () -> new BlockItem(ModBlocks.METEOR_MAGMA.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> CLOTH = ITEMS.register("cloth", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> DISINFECTED_CLOTH = ITEMS.register("disinfected_cloth", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> HERBAL_SOLUTION = ITEMS.register("herbal_solution", () -> new HerbalSolution());

    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> IRIDIUM_INGOT = ITEMS.register("iridium_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> OSMIUM_INGOT = ITEMS.register("osmium_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> SHEPHERDS_PURSE = ITEMS.register("shepherds_purse", () -> new BlockItem(ModBlocks.SHEPHERDS_PURSE.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> FLAMING_SWORD = ITEMS.register("flaming_sword", () -> new FlamingSword());

    public static final RegistryObject<Item> ALLOY_FURNACE = ITEMS.register("alloy_furnace", () -> new BlockItem(ModBlocks.ALLOY_FURNACE.get(), new Item.Properties().tab(ModItemGroups.FUNCTIONAL_BLOCKS)));

    public static final RegistryObject<Item> NICKEL_ORE = ITEMS.register("nickel_ore", () -> new BlockItem(ModBlocks.NICKEL_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> LEAFY_STEM = ITEMS.register("leafy_stem", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<Item> ROTTEN_EGG = ITEMS.register("rotten_egg", () -> new RottenEgg());

    public static final RegistryObject<Item> BLOOD_QUARTZ_ORE = ITEMS.register("blood_quartz_ore", () -> new BlockItem(ModBlocks.BLOOD_QUARTZ_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> FERROX_ORE = ITEMS.register("ferrox_ore", () -> new BlockItem(ModBlocks.FERROX_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> BLOOD_QUARTZ = ITEMS.register("blood_quartz", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> FERROX_POWDER = ITEMS.register("ferrox_powder", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> FERROX_INGOT = ITEMS.register("ferrox_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> OBSIDIAN_POWDER = ITEMS.register("obsidian_powder", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> FLAMING_ORE = ITEMS.register("flaming_ore", () -> new BlockItem(ModBlocks.FLAMING_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> ASH_LOG = ITEMS.register("ash_log", () -> new BlockItem(ModBlocks.ASH_LOG.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> HEART_ITEM = ITEMS.register("heart_item", () -> new HeartItem());

    public static final RegistryObject<Item> MUG_BEER = ITEMS.register("mug_beer", () -> new MugBeer());

    public static final RegistryObject<Item> GLASS_MUG_EMPTY = ITEMS.register("glass_mug_empty", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> ANCIENT_RESIN = ITEMS.register("ancient_resin", () -> new AncientResin());

    public static final RegistryObject<Item> WINE = ITEMS.register("wine", () -> new Wine());

    public static final RegistryObject<Item> EMPTY_WINE_BOTTLE = ITEMS.register("empty_wine_bottle", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> CHEMISTRY_TABLE = ITEMS.register("chemistry_table", () -> new BlockItem(ModBlocks.CHEMISTRY_TABLE.get(), new Item.Properties().tab(ModItemGroups.FUNCTIONAL_BLOCKS)));

    public static final RegistryObject<Item> TEST_TUBE = ITEMS.register("test_tube", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS)));

    public static final RegistryObject<Item> FERROX_ACID_TUBE = ITEMS.register("ferrox_acid_tube", () -> new Item(new Item.Properties().tab(ModItemGroups.CONSUMABLES).stacksTo(1)));

    public static final RegistryObject<Item> FERROX_ACID_BUCKET = ITEMS.register("ferrox_acid_bucket", () -> new BucketItem(ModFluids.FERROX_ACID, new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> BATTERY_BOX = ITEMS.register("battery_box", () -> new BlockItem(ModBlocks.BATTERY_BOX.get(), new Item.Properties().tab(ModItemGroups.FUNCTIONAL_BLOCKS)));

    public static final RegistryObject<Item> REDSTONE_BATTERY = ITEMS.register("redstone_battery", () -> new RedstoneBatteryItem());

    public static final RegistryObject<Item> LARGE_BATTERY = ITEMS.register("large_battery", () -> new LargeBatteryItem());

    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register("solar_panel", () -> new BlockItem(ModBlocks.SOLAR_PANEL.get(), new Item.Properties().tab(ModItemGroups.FUNCTIONAL_BLOCKS)));

    public static final RegistryObject<Item> SUPER_BATTERY = ITEMS.register("super_battery", () -> new SuperBatteryItem());

    public static final RegistryObject<Item> SULFUR_ORE = ITEMS.register("sulfur_ore", () -> new BlockItem(ModBlocks.SULFUR_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> URANIUM_ORE = ITEMS.register("uranium_ore", () -> new BlockItem(ModBlocks.URANIUM_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    // ********** CUSTOM ***********
    public static final RegistryObject<Item> RADIUM = ITEMS.register("radium", () -> new RadioactiveItem(new Item.Properties(), 0.03F));

    public static final RegistryObject<Item> POLONIUM = ITEMS.register("polonium", () -> new RadioactiveItem(new Item.Properties(), 0.02F));

    public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium", () -> new RadioactiveItem(new Item.Properties(), 0.005F));

    // ********** NO CUSTOM ***********
    public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter", () -> new GeigerCounterItem(new Item.Properties().stacksTo(1).tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> ANTIRAD = ITEMS.register("antirad", () -> new AntiRadItem(new Item.Properties().stacksTo(16).tab(ModItemGroups.CONSUMABLES)));

    public static final RegistryObject<Item> NOT_RADIATION = ITEMS.register("not_radiation", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> COPPER_ORE = ITEMS.register("copper_ore", () -> new BlockItem(ModBlocks.COPPER_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

    public static final RegistryObject<Item> RAW_COPPER = ITEMS.register("raw_copper", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS).stacksTo(64)));

    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS).stacksTo(64)));

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(ModItemGroups.MATERIALS).stacksTo(64)));
}
