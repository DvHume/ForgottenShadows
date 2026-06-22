package main.tile;

import main.init.ModTiles;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BatteryBoxTile extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public final CustomEnergyStorage energyStorage = new CustomEnergyStorage(50_000, 10_000, 10_000);
    private final LazyOptional<IEnergyStorage> energyCapability = LazyOptional.of(() -> energyStorage);

    public final ItemStackHandler inventory = new ItemStackHandler(2) {

        @Override
        protected void onContentsChanged(int slot) {setChanged();}
    };

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> inventory);

    public final IIntArray dataAccess = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return BatteryBoxTile.this.energyStorage.getEnergyStored();
                case 1: return BatteryBoxTile.this.energyStorage.getMaxEnergyStored();
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) BatteryBoxTile.this.energyStorage.setEnergy(value);
        }

        @Override
        public int getCount() {return 2;}
    };

    public BatteryBoxTile() {
        super(ModTiles.BATTERY_BOX.get());
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        ItemStack chargeStack = inventory.getStackInSlot(0);
        if (!chargeStack.isEmpty() && chargeStack.getItem() instanceof AbstractBatteryItem) {
            AbstractBatteryItem battery = (AbstractBatteryItem) chargeStack.getItem();
            int currentBoxEnergy = this.energyStorage.getEnergyStored();
            int currentBatteryEnergy = battery.getEnergy(chargeStack);
            int batteryMax = battery.getMaxEnergy();

            if (currentBoxEnergy > 0 && currentBatteryEnergy < batteryMax) {
                int batterySpace = batteryMax - currentBatteryEnergy;
                int transferAmount = Math.min(Math.min(batterySpace, 10000), currentBoxEnergy);
                this.energyStorage.consumeEnergyDirectly(transferAmount);
                battery.setEnergy(chargeStack, currentBatteryEnergy + transferAmount);
                setChanged();
            }
        }

        ItemStack dischargeStack = inventory.getStackInSlot(1);
        if (!dischargeStack.isEmpty() && dischargeStack.getItem() instanceof AbstractBatteryItem) {
            AbstractBatteryItem battery = (AbstractBatteryItem) dischargeStack.getItem();
            int currentBoxEnergy = this.energyStorage.getEnergyStored();
            int maxBoxEnergy = this.energyStorage.getMaxEnergyStored();
            int currentBatteryEnergy = battery.getEnergy(dischargeStack);

            if (currentBoxEnergy < maxBoxEnergy && currentBatteryEnergy > 0) {
                int boxSpace = maxBoxEnergy - currentBoxEnergy;
                int transferAmount = Math.min(Math.min(boxSpace, 10000), currentBatteryEnergy);
                this.energyStorage.addEnergyDirectly(transferAmount);
                battery.setEnergy(dischargeStack, currentBatteryEnergy - transferAmount);
                setChanged();
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) return energyCapability.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.energyStorage.setEnergy(nbt.getInt("Energy"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("Energy", this.energyStorage.getEnergyStored());
        return super.save(nbt);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.frs.battery_box");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new main.container.BatteryBoxContainer(id, playerInventory, this, this.dataAccess);
    }

    public static class CustomEnergyStorage extends EnergyStorage {
        public CustomEnergyStorage(int capability, int maxReceive, int maxExtract) {super(capability, maxReceive, maxExtract);}
        public void setEnergy(int energy) {this.energy = energy;}
        public void addEnergyDirectly(int amount) {this.energy = Math.min(this.energy + amount, this.capacity);}
        public void consumeEnergyDirectly(int amount) {this.energy = Math.max(this.energy - amount, 0);}
    }
}
