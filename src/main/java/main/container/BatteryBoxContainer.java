package main.container;

import main.init.ModContainers;
import main.tile.BatteryBoxTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.SlotItemHandler;

public class BatteryBoxContainer extends Container {
    public final BatteryBoxTile tile;
    private final IIntArray data;

    public BatteryBoxContainer(int id, PlayerInventory playerInventory, BatteryBoxTile tile, IIntArray data) {
        super(ModContainers.BATTERY_BOX.get(), id);
        this.tile = tile;
        this.data = data;
        this.addDataSlots(data);
        this.addSlots(playerInventory);
    }

    public BatteryBoxContainer(int id, PlayerInventory inv, PacketBuffer data) {
        this(id, inv, (BatteryBoxTile) inv.player.level.getBlockEntity(data.readBlockPos()), new net.minecraft.util.IntArray(2));
    }

    protected void addSlots(PlayerInventory playerInventory) {
        this.addSlot(new SlotItemHandler(tile.inventory, 0, 17, 17));
        this.addSlot(new SlotItemHandler(tile.inventory, 1, 143, 17));

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
}
