package main.block;

import net.minecraft.util.IStringSerializable;

public enum RadioactiveType implements IStringSerializable {
    GRASS("dead_grass"),
    SAND("dead_sand"),
    GRAVEL("dead_gravel");

    private final String name;

    RadioactiveType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
