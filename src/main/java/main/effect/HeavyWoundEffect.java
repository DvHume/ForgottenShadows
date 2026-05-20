package main.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class HeavyWoundEffect extends Effect {

    public HeavyWoundEffect() {
        super(EffectType.HARMFUL, 0x8B0000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
