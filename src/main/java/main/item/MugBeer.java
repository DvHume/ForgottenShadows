package main.item;

import main.init.ModEffects;
import main.init.ModItemGroups;
import main.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MugBeer extends Item {

    public MugBeer() {
        super(new Item.Properties()
                .tab(ModItemGroups.CONSUMABLES)
                .stacksTo(1));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(Effects.CONFUSION, 400, 0, false, true));
            player.addEffect(new EffectInstance(ModEffects.FERMENTATION.get(), 400, 0, true, true));
            stack.shrink(1);
            player.inventory.add(new ItemStack(ModItems.GLASS_MUG_EMPTY.get()));
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
