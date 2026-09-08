package main.item.battery;

import main.init.ModItemGroups;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.item.Item;

public class SuperBatteryItem extends AbstractBatteryItem {

    public SuperBatteryItem() {
        super(new Item.Properties().tab(ModItemGroups.CONSUMABLES), 120000);
    }
}
