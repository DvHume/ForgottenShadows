package main.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup BLOCKS = new ItemGroup("frs:blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.TIN_ORE.get());
        }
    };

    public static final ItemGroup FUNCTIONAL_BLOCKS = new ItemGroup("frs:functional_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ALLOY_FURNACE.get());
        }
    };

    public static final ItemGroup MATERIALS = new ItemGroup("frs:materials") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TIN.get());
        }
    };

    public static final ItemGroup CONSUMABLES = new ItemGroup("frs:consumables") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.HERBAL_SOLUTION.get());
        }
    };
}
