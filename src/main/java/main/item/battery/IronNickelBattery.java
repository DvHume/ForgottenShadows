package main.item.battery;

import main.init.ModItemGroups;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.item.Item;

public class IronNickelBattery extends AbstractBatteryItem {
    public IronNickelBattery() {
        super(new Item.Properties().tab(ModItemGroups.CONSUMABLES), 150_000);
    }
}
