package main.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AntiradEffect extends Effect {

    public AntiradEffect() {
        super(EffectType.BENEFICIAL, 0x2DE2E2);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            CompoundNBT nbt = player.getPersistentData();
            float currentDose = nbt.getFloat("RadiationDose");

            if (currentDose > 0.0F) {
                float radiationToRemove = getRadiationToRemove(amplifier);

                float newDose = Math.max(0.0F, currentDose - radiationToRemove);
                nbt.putFloat("RadiationDose", newDose);
            }
        }
    }

    private float getRadiationToRemove(int amplifier) {
        switch (amplifier) {
            case 0:
                return 0.5F;
            case 1:
                return 0.25F;
            case 2:
                return 0.6F;
            default: //Если случайно(или нет) указан уровень которого нет в списке
                return  0.1F;
        }
    }
}
