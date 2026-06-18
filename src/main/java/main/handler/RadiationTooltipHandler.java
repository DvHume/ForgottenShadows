package main.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class RadiationTooltipHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CompoundNBT nbt = stack.getTag();

        if (nbt != null && nbt.contains("InfectedRad")) {
            float infectedRad = nbt.getFloat("InfectedRad");

            if (infectedRad > 0.0F) {
                List<ITextComponent> tooltip = event.getToolTip();
                if (tooltip.size() > 1 && !tooltip.get(tooltip.size() - 1).getString().isEmpty()) {
                    tooltip.add(new StringTextComponent(" "));
                }

                TextFormatting valueColor;
                if (infectedRad < 0.02F) {
                    valueColor = TextFormatting.GREEN;
                } else if (infectedRad < 0.05F) {
                    valueColor = TextFormatting.YELLOW;
                } else {
                    valueColor = TextFormatting.RED;
                }

                ITextComponent strictTag = new TranslationTextComponent("tooltip.frs.radiation")
                        .withStyle(TextFormatting.DARK_AQUA)
                        .append(new StringTextComponent(String.format("+%.3f Rad", infectedRad))
                                .withStyle(valueColor));

                tooltip.add(strictTag);
            }
        }
    }
}
