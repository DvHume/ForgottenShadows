package main.container;

import main.init.ModContainers;
import main.tile.AlloyFurnaceTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class AlloyFurnaceContainer extends Container {
    public final AlloyFurnaceTile tile;
    public AlloyFurnaceContainer(int id, PlayerInventory playerInventory, AlloyFurnaceTile tile) {
        super(ModContainers.ALLOY_FURNACE.get(), id);
        this.tile = tile;

        this.addSlot(new SlotItemHandler(tile.inventory, 0, 26, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 1, 62, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 2, 98, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 4, 134, 35));

        this.addSlot(new SlotItemHandler(tile.inventory, 3, 62, 53));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }
}
