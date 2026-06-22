package main.item.other;

import main.init.ModEffects;
import main.init.ModItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RadioactiveItem extends Item {
    private final float radiationPerSec;

    public RadioactiveItem(Properties properties, float radiationPerSec) {
        super(properties.tab(ModItemGroups.MATERIALS).stacksTo(64));
        this.radiationPerSec = radiationPerSec;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slotId, boolean isSelected) {
        if (!world.isClientSide && entity instanceof PlayerEntity) {
            if (entity.tickCount % 20 == 0) {
                PlayerEntity player = (PlayerEntity) entity;
                CompoundNBT nbt = player.getPersistentData();
                float currentDose = nbt.getFloat("RadiationDose");
                nbt.putFloat("RadiationDose", currentDose + (this.radiationPerSec * stack.getCount()));
                player.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 200, 0));
            }
        }
        super.inventoryTick(stack, world, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        float mRadValue = this.radiationPerSec * 1000F;

        TextFormatting valueColor;
        if (mRadValue <= 10F) {
            valueColor = TextFormatting.GREEN;
        } else if (mRadValue <= 50) {
            valueColor = TextFormatting.YELLOW;
        } else {
            valueColor = TextFormatting.RED;
        }

        tooltip.add(new TranslationTextComponent("tooltip.frs.radiation.source")
                .withStyle(TextFormatting.DARK_GREEN)
                .append(new StringTextComponent(String.format(" %.0f mRad/s", mRadValue))
                        .withStyle(valueColor)));

        super.appendHoverText(stack, world, tooltip, flag);
    }

    // Геттер для чтения
    public float getRadiationPerSec() {
        return this.radiationPerSec;
    }
}
