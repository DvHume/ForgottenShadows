package main.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.DamageSource;

public class DarkSteelSpear extends TridentItem {

    public DarkSteelSpear() {
        super(new Item.Properties()
                .tab(ItemGroup.TAB_COMBAT)
                .durability(500)
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurt(DamageSource.MAGIC, 8.0f);
        stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(
                attacker instanceof PlayerEntity ?
                        ((PlayerEntity) attacker).getUsedItemHand() :
                        net.minecraft.util.Hand.MAIN_HAND
        ));
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        if (enchantment == net.minecraft.enchantment.Enchantments.LOYALTY) return false;
        if (enchantment == net.minecraft.enchantment.Enchantments.RIPTIDE) return false;
        if (enchantment == net.minecraft.enchantment.Enchantments.CHANNELING) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}