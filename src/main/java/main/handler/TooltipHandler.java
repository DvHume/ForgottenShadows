package main.handler;

import main.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TooltipHandler {
    private static final TextFormatting[] RAINBOW = {
            TextFormatting.RED,
            TextFormatting.GOLD,
            TextFormatting.YELLOW,
            TextFormatting.GREEN,
            TextFormatting.AQUA,
            TextFormatting.LIGHT_PURPLE
    };

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() == ModItems.DIVINE_SWORD.get()) {
            long tick = System.currentTimeMillis() / 100;
            TextFormatting color = RAINBOW[(int)(tick % RAINBOW.length)];

            event.getToolTip().add(new TranslationTextComponent("item.frs.divine_sword.tooltip").withStyle(TextFormatting.YELLOW));

            event.getToolTip().add(new StringTextComponent(" "));

            event.getToolTip().add(new TranslationTextComponent("item.frs.divine_sword.tooltip2").withStyle(color));
        }

        if (stack.getItem() == ModItems.ALLOY_FURNACE.get()) {
            long tick = System.currentTimeMillis() / 100;
            TextFormatting color = RAINBOW[(int)(tick % RAINBOW.length)];
            // Обычное описание
            event.getToolTip().add(new TranslationTextComponent("block.frs.alloy_furnace.tooltip")
                    .withStyle(color));

            event.getToolTip().add(new StringTextComponent(" "));

            // Описание при зажатом Shift
            if (net.minecraft.client.gui.screen.Screen.hasShiftDown()) {
                event.getToolTip().add(new TranslationTextComponent("block.frs.alloy_furnace.tooltip_shift")
                        .withStyle(TextFormatting.RED));
            } else {
                // Подсказка что есть доп. описание
                event.getToolTip().add(new TranslationTextComponent("tooltip.frs.hold_shift")
                        .withStyle(TextFormatting.YELLOW));
            }
        }

        if (stack.getItem() == ModItems.FLAMING_PICKAXE.get()) {
            CompoundNBT nbt = stack.getOrCreateTag();
            boolean smelting = nbt.getBoolean("smelting");

            event.getToolTip().add(new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip")
                    .withStyle(TextFormatting.LIGHT_PURPLE));

            event.getToolTip().add(new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_mode")
                    .withStyle(TextFormatting.GRAY));

            if (smelting) {
                event.getToolTip().add(new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_on")
                        .withStyle(TextFormatting.GREEN));
            } else {
                event.getToolTip().add(new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_off")
                        .withStyle(TextFormatting.RED));
            }

            event.getToolTip().add(new StringTextComponent(" "));

            event.getToolTip().add(new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_hint")
                    .withStyle(TextFormatting.YELLOW));
        }
    }
}
