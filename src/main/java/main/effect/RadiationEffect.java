package main.effect;

import main.init.utils.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class RadiationEffect extends Effect {
    private static final float LETHAL_LEVEL = 4.0F;

    public RadiationEffect() {
        super(EffectType.HARMFUL, 0x46C612);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide) {
            CompoundNBT nbt = entity.getPersistentData();
            float currentDose = nbt.getFloat("RadiationDose");

            if (currentDose >= LETHAL_LEVEL) {
                float finalDamage = 1.0F + ((currentDose - LETHAL_LEVEL) * 2.0F);
                entity.hurt(ModDamageSources.RADIATION, finalDamage);
            }
            nbt.putFloat("RadiationDose", Math.max(0, currentDose - 0.005F));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
