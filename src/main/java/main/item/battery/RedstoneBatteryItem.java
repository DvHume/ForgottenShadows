package main.item.battery;

import main.init.ModItemGroups;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.item.Item;

public class RedstoneBatteryItem extends AbstractBatteryItem {

    public RedstoneBatteryItem() {
        super(new Item.Properties().tab(ModItemGroups.CONSUMABLES).stacksTo(1), 30000);
    }
}
