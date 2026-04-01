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

            event.getToolTip().add(1, new TranslationTextComponent("item.frs.divine_sword.tooltip").withStyle(TextFormatting.YELLOW));

            event.getToolTip().add(2, new StringTextComponent(" "));

            event.getToolTip().add(3, new TranslationTextComponent("item.frs.divine_sword.tooltip2").withStyle(color));
        }

        if (stack.getItem() == ModItems.FLAMING_PICKAXE.get()) {
            CompoundNBT nbt = stack.getOrCreateTag();
            boolean smelting = nbt.getBoolean("smelting");

            event.getToolTip().add(1, new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip")
                    .withStyle(TextFormatting.LIGHT_PURPLE));

            event.getToolTip().add(2, new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_mode")
                    .withStyle(TextFormatting.GRAY));

            if (smelting) {
                event.getToolTip().add(3,new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_on")
                        .withStyle(TextFormatting.GREEN));
            } else {
                event.getToolTip().add(3, new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_off")
                        .withStyle(TextFormatting.RED));
            }

            event.getToolTip().add(4, new StringTextComponent(" "));

            event.getToolTip().add(5, new TranslationTextComponent("item.frs.flaming_pickaxe.tooltip_hint")
                    .withStyle(TextFormatting.YELLOW));
        }

        if (stack.getItem() == ModItems.HERBAL_BANDAGE.get()) {
            event.getToolTip().add(1, new TranslationTextComponent("item.frs.herbal_bandage.tooltip")
                    .withStyle(TextFormatting.GOLD));
            event.getToolTip().add(2, new TranslationTextComponent("item.frs.herbal_bandage.tool")
                    .withStyle(TextFormatting.RED));
        }

        if (stack.getItem() == ModItems.VIRTON_SHARD.get()) {
            event.getToolTip().add(1, new TranslationTextComponent("item.frs.virton_shard.tooltip")
                    .withStyle(TextFormatting.LIGHT_PURPLE));
        }

        if (stack.getItem() == ModItems.HERBAL_SOLUTION.get()) {
            event.getToolTip().add(1, new TranslationTextComponent("item.frs.herbal_solution.tooltip")
                    .withStyle(TextFormatting.GOLD));
            event.getToolTip().add(2, new TranslationTextComponent("item.frs.herbal_solution.tool")
                    .withStyle(TextFormatting.GREEN));
        }

        if (stack.getItem() == ModItems.ROTTEN_EGG.get()) {
            event.getToolTip().add(1, new TranslationTextComponent("item.frs.rotten_egg.tooltip")
                    .withStyle(TextFormatting.GREEN));
            event.getToolTip().add(2, new TranslationTextComponent("item.frs.rotten_egg.tool")
                    .withStyle(TextFormatting.DARK_RED));
        }
    }
}
