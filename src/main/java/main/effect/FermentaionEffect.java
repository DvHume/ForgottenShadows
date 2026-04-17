package main.effect;

import main.init.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;

public class FermentaionEffect extends Effect {

    public FermentaionEffect() {
        super(EffectType.NEUTRAL, 0xFFAA00);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level.isClientSide) return;

        if (entity.getRandom().nextInt(100) == 0) {
            entity.level.playSound(null, entity.blockPosition(),
                    ModSounds.STOMACH_GURGLE.get(),
                    SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
