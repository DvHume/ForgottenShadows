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

    public static final ItemGroup CONSUMABLES = new ItemGroup("frs:consumables") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.HERBAL_SOLUTION.get());
        }
    };
}
