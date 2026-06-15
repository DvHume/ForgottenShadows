package main.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class AntiRadItem extends Item {
    public AntiRadItem(Properties properties) {
        super(properties.food(new Food.Builder().alwaysEat().fast().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            CompoundNBT nbt = player.getPersistentData();
            nbt.putInt("AntiradTimer", 600);
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
