package main.effect;

import main.init.utils.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class BleedingEffect  extends Effect {
    public BleedingEffect() {
        super(EffectType.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        float damage = 0.5f * (amplifier + 1);
        entity.hurt(ModDamageSources.BLEEDING, damage);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }
}
