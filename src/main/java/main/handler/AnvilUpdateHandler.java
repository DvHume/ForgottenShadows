package main.handler;

import main.item.DarkSteelSpear;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
@Mod.EventBusSubscriber
public class AnvilUpdateHandler {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();

        if (!(left.getItem() instanceof DarkSteelSpear)) return;

        ItemStack right = event.getRight();
        if (right.getItem() != Items.ENCHANTED_BOOK) return;

        // Проверяем есть ли запрещённые зачарования в книге
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(right);

        if (enchants.containsKey(Enchantments.RIPTIDE) ||
                enchants.containsKey(Enchantments.LOYALTY) ||
                enchants.containsKey(Enchantments.CHANNELING)) {
            event.setCanceled(true); // блокируем применение
        }
    }
}
