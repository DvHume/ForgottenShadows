package main.item.battery;

import main.init.ModItemGroups;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.item.Item;

public class LargeBatteryItem extends AbstractBatteryItem {

    public LargeBatteryItem() {
        super(new Item.Properties().tab(ModItemGroups.CONSUMABLES), 80000);
    }
}
