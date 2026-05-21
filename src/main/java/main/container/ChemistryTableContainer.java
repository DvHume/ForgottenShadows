package main.container;

import main.init.ModContainers;
import main.tile.ChemistryTableTile;
import main.init.abstractclass.AbstractBatteryItem;
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
        this(id, inv, (ChemistryTableTile) inv.player.level.getBlockEntity(data.readBlockPos()), new net.minecraft.util.IntArray(4));
    }

    protected void addSlots(PlayerInventory playerInventory) {
        this.addSlot(new SlotItemHandler(tile.inventory, 0, 59, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 1, 79, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 2, 99, 17));

        this.addSlot(new SlotItemHandler(tile.inventory, 3, 59, 56));
        this.addSlot(new SlotItemHandler(tile.inventory, 4, 79, 56));
        this.addSlot(new SlotItemHandler(tile.inventory, 5, 99, 56));

        this.addSlot(new SlotItemHandler(tile.inventory, 6, 17, 17));

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

    public int getFuelScaled() {
        int fuel = this.data.get(2);
        int maxFuel = this.data.get(3);
        return maxFuel != 0 ? fuel * 46 / maxFuel : 0;
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

            if (index < 7) {
                if (!this.moveItemStackTo(itemStack1, 7, 43, true)) return ItemStack.EMPTY;
            } else {
                // fixed: shift-click now checks batteries and inserts them into the "fuel" slot (hmm...)
                if (itemStack1.getItem() instanceof AbstractBatteryItem) {
                    if (!this.moveItemStackTo(itemStack1, 6, 7, false)) {
                        if (!this.moveItemStackTo(itemStack1, 0, 3, false)) return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemStack1, 0, 3, false)) return ItemStack.EMPTY;
                }
            }

            if (itemStack1.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemStack;
    }
}