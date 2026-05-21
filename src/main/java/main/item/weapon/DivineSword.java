package main.item.weapon;

import main.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;

public class DivineSword extends SwordItem {
    public DivineSword() {
        super(ItemTier.NETHERITE, 9, -2.4F, new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attaker) {
        if (!attaker.level.isClientSide) {
            target.addEffect(new EffectInstance(ModEffects.HEAVY_WOUND.get(), 600, 0));
        }
        return super.hurtEnemy(stack, target, attaker);
    }
}