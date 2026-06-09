package main.item.Tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GeigerCounterItem extends Item {
    public GeigerCounterItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            CompoundNBT nbt = player.getPersistentData();
            float currentDose = nbt.getFloat("RadiationDose");
            TextFormatting color = TextFormatting.GREEN;
            if (currentDose >= 1.0F && currentDose < 4.0F) color = TextFormatting.YELLOW;
            if (currentDose >= 4.0F) color = TextFormatting.RED;
            String message = String.format("Radiation: %.3f Rad/s", currentDose);
            player.sendMessage(new StringTextComponent(message).withStyle(color), player.getUUID());
        }
        return ActionResult.success(player.getItemInHand(hand));
    }
}
