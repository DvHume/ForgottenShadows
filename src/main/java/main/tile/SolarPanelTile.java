package main.tile;

import main.init.ModTiles;
import main.init.abstractclass.BaseEnergyTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.CapabilityEnergy;

public class SolarPanelTile extends BaseEnergyTile implements ITickableTileEntity {
    private int tickCounter = 0;

    public SolarPanelTile() {
        super(ModTiles.SOLAR_PANEL.get(), 20000, 0, 20);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        tickCounter++;
        if (this.tickCounter < 20) return;
        this.tickCounter = 0;

        // FUCK
        // I don't know why, but when I check on "canSeeSky", everything just stops working
        if (level.getSkyDarken() < 9) {
            int energyToGenerate = 200;
            this.energyStorage.addEnergyDirectly(energyToGenerate);

            System.out.println("to generation " + this.energyStorage.getEnergyStored());
            setChanged();
        }
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);


        // Exit slot
        int myEnergy = this.getStoredEnergy();
        if (myEnergy > 0) {
            TileEntity targetDown = level.getBlockEntity(worldPosition.below());
            if (targetDown != null) {
                targetDown.getCapability(CapabilityEnergy.ENERGY, Direction.UP).ifPresent(targetStorage -> {
                    if (targetStorage.canReceive()) {
                        int toSend = this.energyStorage.extractEnergy(20, true);
                        int accepted = targetStorage.receiveEnergy(toSend, false);
                        this.energyStorage.extractEnergy(accepted, false);
                        setChanged();
                    }
                });
            }
        }
    }

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