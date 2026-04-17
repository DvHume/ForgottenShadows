package main.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "frs");

    public static final RegistryObject<SoundEvent> HERBAL_BANDAGE_USE = SOUNDS.register("herbal_bandage_use", () -> new SoundEvent(new ResourceLocation("frs", "herbal_bandage_use")));

    public static final RegistryObject<SoundEvent> MODE_SWITCH = SOUNDS.register("mode_switch", () -> new SoundEvent(new ResourceLocation("frs", "mode_switch")));

    public static final RegistryObject<SoundEvent> METEOR_FLY = SOUNDS.register("meteor_fly", () -> new SoundEvent(new ResourceLocation("frs", "meteor_fly")));

    public static final RegistryObject<SoundEvent> METEOR_IMPACT = SOUNDS.register("meteor_impact", () -> new SoundEvent(new ResourceLocation("frs", "meteor_impact")));

    public static final RegistryObject<SoundEvent> IRON_GOLEM_EN = SOUNDS.register("iron_golem_en", () -> new SoundEvent(new ResourceLocation("frs", "iron_golem_en")));

    public static final RegistryObject<SoundEvent> IRON_GOLEM_RU = SOUNDS.register("iron_golem_ru", () -> new SoundEvent(new ResourceLocation("frs", "iron_golem_ru")));

    public static final RegistryObject<SoundEvent> STOMACH_GURGLE = SOUNDS.register("stomach_gurgle", () -> new SoundEvent(new ResourceLocation("frs", "stomach_gurgle")));
}
