package main.init.abstractclass;

import api.IEnergyTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseEnergyTile extends TileEntity implements IEnergyTile {

    protected final CustomEnergyStorage  energyStorage;
    protected final LazyOptional<IEnergyStorage> energyCapability;

    public BaseEnergyTile(TileEntityType<?> tileEntityType, int capacity, int maxReceive, int maxExtract) {
        super(tileEntityType);
        this.energyStorage = new CustomEnergyStorage(capacity, maxReceive, maxExtract);
        this.energyCapability = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public IEnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.energyStorage.setEnergyDirectly(nbt.getInt("Energy"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("Energy", this.energyStorage.getEnergyStored());
        return super.save(nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return new SUpdateTileEntityPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (pkt != null && pkt.getTag() != null) {
            this.load(this.getBlockState(), pkt.getTag());
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        if (tag != null) {
            this.load(state, tag);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) return energyCapability.cast();
        return super.getCapability(cap, side);
    }

    public static class CustomEnergyStorage extends EnergyStorage {
        public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {super(capacity, maxReceive, maxExtract);}
        public void setEnergyDirectly(int amount) {this.energy = amount;}
        public void addEnergyDirectly(int amount) {
            this.energy = Math.min(this.capacity, this.energy + amount);
        }
    }
}
