package main.item.other;

import main.init.ModEffects;
import main.init.ModItemGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class RadioactiveItem extends Item {
    private final float radiationPerSec;

    public RadioactiveItem(Properties properties, float radiationPerSec) {
        super(properties.tab(ModItemGroups.MATERIALS).stacksTo(64));
        this.radiationPerSec = radiationPerSec;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            boolean isHand = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
            if (isHand && entity.tickCount % 20 ==0) {
                CompoundNBT nbt = player.getPersistentData();
                float currentDose = nbt.getFloat("RadiationDose");
                nbt.putFloat("RadiationDose", currentDose + this.radiationPerSec);
                player.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 400, 0));
            }
        }
        super.inventoryTick(stack, world, entity, slotId, isSelected);
    }
}
