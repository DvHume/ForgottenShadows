package main.tile;

import main.init.ModTiles;
import main.init.abstractclass.BaseEnergyTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.LightType;
import net.minecraftforge.energy.CapabilityEnergy;

public class SolarPanelTile extends BaseEnergyTile implements ITickableTileEntity {
    private int tickCounter = 0;
    private int lastEnergy = 0;

    public SolarPanelTile() {
        super(ModTiles.SOLAR_PANEL.get(), 20_000, 0, 1_000);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        tickCounter++;
        if (this.tickCounter < 20) return;
        this.tickCounter = 0;

        /*
         Check: if the buffer is full to the maximum(20k)
         And since the last second no one has taken energy(the current one is equal to the previous one)
        */
        if (this.energyStorage.getEnergyStored() >= this.energyStorage.getMaxEnergyStored()
                && this.energyStorage.getEnergyStored() == this.lastEnergy) {return; } // just exit


        if (level.isDay() && level.getBrightness(LightType.SKY, worldPosition.above()) > 10) {
            int energyToGenerate = 300;
            this.energyStorage.addEnergyDirectly(energyToGenerate);
            setChanged();
        }
        // At the end of the method, save the remaining energy
        this.lastEnergy = this.energyStorage.getEnergyStored();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);


        // Exit slot
        int myEnergy = this.getStoredEnergy();
        if (myEnergy > 0) {
            TileEntity targetDown = level.getBlockEntity(worldPosition.below());
            if (targetDown != null) {
                targetDown.getCapability(CapabilityEnergy.ENERGY, Direction.UP).ifPresent(targetStorage -> {
                    if (targetStorage.canReceive()) {
                        int toSend = this.energyStorage.extractEnergy(1_000, true);
                        int accepted = targetStorage.receiveEnergy(toSend, false);
                        this.energyStorage.extractEnergy(accepted, false);
                        setChanged();
                    }
                });
            }
        }
    }

    // So that when the world is rebooted, the timer does not reset
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.tickCounter = nbt.getInt("TickCounter");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("TickCounter", this.tickCounter);
        return super.save(nbt);
    }
}