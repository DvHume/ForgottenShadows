package main.init.abstractclass;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractBatteryItem extends Item {
    private final int maxEnergy;

    public AbstractBatteryItem(Properties properties, int maxEnergy) {
        super(properties.stacksTo(1));
        this.maxEnergy = maxEnergy;
    }

    public int getMaxEnergy() {return this.maxEnergy;}

    public int getEnergy(ItemStack stack) {
        if (!stack.hasTag()) return this.maxEnergy;
        return stack.getTag().getInt("Energy");
    }

    public void setEnergy(ItemStack stack, int energy) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt("Energy", Math.max(0, Math.min(energy, this.maxEnergy)));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {return true;}

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - ((double) getEnergy(stack) / (double) this.maxEnergy);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        float ratio = (float) getEnergy(stack) / (float) this.maxEnergy;
        return net.minecraft.util.math.MathHelper.hsvToRgb(ratio / 3.0f, 1.0f, 1.0f);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        int currentlyEnergy = getEnergy(stack);
        int percent = (int) (((double) currentlyEnergy / (double) this.maxEnergy) * 100);

        tooltip.add(new TranslationTextComponent("tooltip.frs.battery.energy")
                .append(new StringTextComponent(": " + currentlyEnergy + " / " + this.maxEnergy + "FE"))
                .withStyle(TextFormatting.GRAY));

        TextFormatting color = TextFormatting.GREEN;
        if (percent <= 20) color = TextFormatting.RED;
        else if (percent <= 50) color = TextFormatting.YELLOW;

        tooltip.add(new TranslationTextComponent("tooltip.frs.battery.charge")
                .append(new StringTextComponent(": " + percent + "%"))
                .withStyle(color));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public void fillItemCategory(net.minecraft.item.ItemGroup group, net.minecraft.util.NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ItemStack emptyStack = new ItemStack(this);
            this.setEnergy(emptyStack, 0);
            items.add(emptyStack);

            ItemStack fullStack = new ItemStack(this);
            this.setEnergy(fullStack, this.maxEnergy);
            items.add(fullStack);
        }
    }
}
