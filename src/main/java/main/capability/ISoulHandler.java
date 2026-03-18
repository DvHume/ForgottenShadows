package main.capability;

public interface ISoulHandler {
    float getSouls();
    void setSouls(float souls);
    void addSouls(float amount);
    float getMaxSouls();
    float getMinSouls();
}
