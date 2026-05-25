package api;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyTile {
    /** Get access to the internal energy storage */
    IEnergyStorage getEnergyStorage();

    /** Shortcuts to avoid calling the getter every time */
    default int getStoredEnergy() {return getEnergyStorage().getEnergyStored(); }
    default int getMaxEnergy() {return getEnergyStorage().getMaxEnergyStored(); }
}
