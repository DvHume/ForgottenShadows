package main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue ENABLE_DESERT_SUNBURN;

    static {
        BUILDER.push("General Settings");
        ENABLE_DESERT_SUNBURN = BUILDER
                .comment("Enable desert tanning/burn mechanics? (true = enabled, false = disabled")
                .define("enableDesertSunburn", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
