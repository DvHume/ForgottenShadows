package main.item;

import main.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class AntiRadItem extends Item {
    public AntiRadItem(Properties properties) {
        super(properties.food(new Food.Builder().alwaysEat().fast().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(ModEffects.ANTIRAD.get(), 200, 1));
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
