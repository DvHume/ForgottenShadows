/*package main.capability;

import net.minecraft.nbt.CompoundNBT;

public class SoulHandler implements ISoulHandler{
    private float souls = 100.0f; //default
    private  static final float MAX_SOULS = 200.0f;
    private static final float MIN_SOULS = -200.0f;

    @Override
    public float getSouls() {
        return souls;
    }

    @Override
    public void setSouls(float souls) {
        this.souls = Math.max(MIN_SOULS, Math.min(MAX_SOULS, souls));
    }

    @Override
    public void addSouls(float amount) {
        setSouls(this.souls + amount);
    }

    @Override
    public float getMaxSouls() {
        return MAX_SOULS;
    }

    @Override
    public float getMinSouls() {
        return MIN_SOULS;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("souls", souls);
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        souls = nbt.getFloat("souls");
    }
}
*/