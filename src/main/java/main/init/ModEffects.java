package main.init;

import main.effect.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "frs");

    public static final RegistryObject<Effect> HEAVY_WOUND = EFFECTS.register("heavy_wound", HeavyWoundEffect::new);

    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

    //public static final RegistryObject<Effect> CURSE_SOUL = EFFECTS.register("curse_soul", CurseSoulEffect::new);

    public static final RegistryObject<Effect> GOOD_REST = EFFECTS.register("good_rest", GoodRestEffect::new);

    public static final RegistryObject<Effect> FERMENTATION = EFFECTS.register("fermentation", FermentaionEffect::new);

    public static final RegistryObject<Effect> SUNBURN = EFFECTS.register("sunburn", SunburnEffect::new);

    public static final RegistryObject<Effect> RADIATION = EFFECTS.register("radiation", RadiationEffect::new);

    public static final RegistryObject<Effect> ANTIRAD = EFFECTS.register("antirad", AntiradEffect::new);
}