package main.item;

import main.entity.RottenEggEntity;
import main.init.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class RottenEgg extends Item {

    public RottenEgg() {
        super(new Item.Properties()
                .tab(ModItemGroups.CONSUMABLES)
                .stacksTo(16)
        );
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundCategory.PLAYERS,
                0.5f, 0.4f / (world.random.nextFloat() * 0.4f + 0.8f));

        if (!world.isClientSide) {
            RottenEggEntity egg = new RottenEggEntity(world, player);
            egg.setItem(stack);
            egg.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 1.5f, 1.0f);
            world.addFreshEntity(egg);
        }
        if (!player.abilities.instabuild) {
            stack.shrink(1);
        }
        return ActionResult.success(stack);
    }
}