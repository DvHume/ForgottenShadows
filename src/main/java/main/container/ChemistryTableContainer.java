package main.container;

import main.init.ModContainers;
import main.tile.ChemistryTableTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.SlotItemHandler;

public class ChemistryTableContainer extends Container {
    public final ChemistryTableTile tile;
    private final IIntArray data;
    public ChemistryTableContainer(int id, PlayerInventory playerInventory, ChemistryTableTile tile, IIntArray data) {
        super(ModContainers.CHEMISTRY_TABLE.get(), id);
        this.tile = tile;
        this.data = data;
        this.addDataSlots(data);
        this.addSlots(playerInventory);
    }

    public ChemistryTableContainer(int id, PlayerInventory inv, PacketBuffer data) {
        this(id, inv, (ChemistryTableTile) inv.player.level.getBlockEntity(data.readBlockPos()), new net.minecraft.util.IntArray(2));
    }

    protected void addSlots(PlayerInventory playerInventory) {
        this.addSlot(new SlotItemHandler(tile.inventory, 0, 44, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 1, 62, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 2, 80, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 3, 62, 53));

        this.addSlot(new SlotItemHandler(tile.inventory, 4, 134, 35));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public int getProgressionScaled() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        return maxProgress != 0 && progress != 0 ? progress * 24 / maxProgress : 0;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {return true;}

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (index < 5) {
                if (!this.moveItemStackTo(itemStack1, 5, 41, true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(itemStack1, 0, 4, false)) return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemStack;
    }
}
