package main.item;

import main.init.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AncientResin extends Item {

    public AncientResin() {
        super(new Item.Properties()
                .tab(ModItemGroups.CONSUMABLES)
                .stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.pass(stack);
        }

        player.sendMessage(new TranslationTextComponent("item.frs.ancient_resin.inf"),
                player.getUUID());
        return ActionResult.success(player.getItemInHand(hand));
    }
}
