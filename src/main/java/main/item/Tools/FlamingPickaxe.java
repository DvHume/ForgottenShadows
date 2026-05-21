package main.item.Tools;

import main.init.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FlamingPickaxe extends PickaxeItem {
    public FlamingPickaxe() {
        super(ItemTier.IRON, 1, -2.8f, new Item.Properties().tab(ItemGroup.TAB_TOOLS));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.MODE_SWITCH.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);

            if (!world.isClientSide) {
                CompoundNBT nbt = stack.getOrCreateTag();
                boolean current = nbt.getBoolean("smelting");
                nbt.putBoolean("smelting", !current);

                if (!current) {
                    player.displayClientMessage(new TranslationTextComponent("item.frs.flaming_pickaxe.on").withStyle(TextFormatting.GREEN), true);
                } else {
                    player.displayClientMessage(new TranslationTextComponent("item.frs.flaming_pickaxe.off").withStyle(TextFormatting.RED), true);
                }
            }
            return ActionResult.success(stack);
        }
        return ActionResult.pass(stack);
    }
}
