package main.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "frs");

    public static final RegistryObject<SoundEvent> HERBAL_BANDAGE_USE = SOUNDS.register("herbal_bandage_use", () -> new SoundEvent(new ResourceLocation("frs", "herbal_bandage_use")));

    public static final RegistryObject<SoundEvent> MODE_SWITCH = SOUNDS.register(
            "mode_switch",
            () -> new SoundEvent(new ResourceLocation("frs", "mode_switch"))
    );
}
