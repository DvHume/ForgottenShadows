package main.handler;

import main.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LootTableHandler {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String name = event.getName().toString();

        if (name.equals("minecraft:chests/simple_dungeon") ||
                name.equals("minecraft:chests/abandoned_mineshaft") ||
                name.equals("minecraft:chests/village/village_weaponsmith") ||
                name.equals("minecraft:chests/stronghold_corridor")) {

            event.getTable().addPool(
                    LootPool.lootPool()
                            .add(ItemLootEntry.lootTableItem(ModItems.CLOTH.get())
                                    .setWeight(15)
                                    .apply(SetCount.setCount(
                                            RandomValueRange.between(1, 3)
                                    ))
                            )
                            .add(EmptyLootEntry.emptyItem().setWeight(85))
                            .build()
            );
        }
    }
}