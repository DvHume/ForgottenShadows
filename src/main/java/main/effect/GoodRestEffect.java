package main.effect;

import main.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class GoodRestEffect extends Effect {

    public GoodRestEffect() {
        super(EffectType.BENEFICIAL, 0xFFD700);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}