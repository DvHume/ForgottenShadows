package main.item.weapon;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class FlamingSword extends SwordItem {

    public FlamingSword() {
        super(ItemTier.NETHERITE, 6, -2.4F, new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attaker) {
        if (!attaker.level.isClientSide) {
            target.setSecondsOnFire(5);
        }
        return super.hurtEnemy(stack, target, attaker);
    }
}

