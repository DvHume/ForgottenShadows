package main.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue ENABLE_DESERT_SUNBURN;
    public static ForgeConfigSpec.DoubleValue FATAL_RADIATION_DOSE;

    static {
        BUILDER.push("General Settings");
        ENABLE_DESERT_SUNBURN = BUILDER
                .comment("Enable desert tanning/burn mechanics? (true = enabled, false = disabled")
                .define("enableDesertSunburn", true);

        FATAL_RADIATION_DOSE = BUILDER
                .comment("At what radiation dose level does the player start taking damage? Max allowed: 50.0 (Default: 4.0)")
                .defineInRange("fatalRadiationDose", 6.0D, 0.5D, 50D);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
