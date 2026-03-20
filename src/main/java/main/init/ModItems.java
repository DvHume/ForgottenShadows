package main.init;

import main.item.DarkSteelSpear;
import main.item.DivineSword;
import main.item.FlamingPickaxe;
import main.item.HerbalBandage;
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

    public static final RegistryObject<Item> ALLOY_FURNACE = ITEMS.register(
            "alloy_furnace",
            () -> new BlockItem(ModBlocks.ALLOY_FURNACE.get(), new Item.Properties())
    );
            //TIN - я забыл добавить ingot извините за путаницу
    public static final RegistryObject<Item> TIN = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore", () -> new BlockItem(ModBlocks.TIN_ORE.get(), new Item.Properties().tab(ModItemGroups.BLOCKS)));

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
}
