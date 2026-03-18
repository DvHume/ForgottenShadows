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
}
