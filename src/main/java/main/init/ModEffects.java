package main.init;

import main.effect.CurseSoulEffect;
import main.effect.GoodRestEffect;
import main.effect.HeavyWoundEffect;
import main.effect.BleedingEffect;
import main.item.DivineSword;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.registries.ForgeRegistries.Keys.EFFECTS;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "frs");

    public static final RegistryObject<Effect> HEAVY_WOUND = EFFECTS.register("heavy_wound", HeavyWoundEffect::new);

    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

    public static final RegistryObject<Effect> CURSE_SOUL = EFFECTS.register("curse_soul", CurseSoulEffect::new);

    public static final RegistryObject<Effect> GOOD_REST = EFFECTS.register("good_rest", GoodRestEffect::new);
}