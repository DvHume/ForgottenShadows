package main.item.battery;

import main.init.ModItemGroups;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.item.Item;

public class GreenBattery extends AbstractBatteryItem {

    public GreenBattery() {
        super(new Item.Properties().tab(ModItemGroups.CONSUMABLES), 1500);
    }
}
