package main.item;

import main.init.ModItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HerbalSolution extends Item {
    public HerbalSolution() {
        super(new Item.Properties()
                .tab(ModItemGroups.CONSUMABLES)
                .stacksTo(16));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(Effects.POISON, 400, 0, false, true));
            stack.shrink(1);
            if (!player.isCreative()) {
                player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.startUsingItem(hand);
        return ActionResult.consume(player.getItemInHand(hand));
    }
}
