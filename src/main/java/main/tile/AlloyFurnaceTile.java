package main.tile;

import main.container.AlloyFurnaceContainer;
import main.init.ModItems;
import main.init.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;


/* ------------------
    Obsolete method
   ------------------*/

public class AlloyFurnaceTile extends TileEntity implements INamedContainerProvider, ITickableTileEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int progress = 0;
    private int maxProgress = 200;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public AlloyFurnaceTile() {
        super(ModTiles.ALLOY_FURNACE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("progress", progress);
        nbt.putInt("fuelTime", fuelTime);
        nbt.putInt("maxFuelTime", maxFuelTime);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
        fuelTime = nbt.getInt("fuelTime");
        maxFuelTime = nbt.getInt("maxFuelTime");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.frs.alloy_furnace");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlloyFurnaceContainer(id, playerInventory, this);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        boolean hasFuel = fuelTime > 0;
        if (!hasFuel) {
            ItemStack fuelStack = inventory.getStackInSlot(3);
            int burnTime = ForgeHooks.getBurnTime(fuelStack, null);
            if (burnTime > 0) {
                maxFuelTime = burnTime;
                fuelTime = burnTime;
                inventory.extractItem(3, 1, false);
                setChanged();
            }
        }
        boolean hasIngredients = hasRecipe();
        if (fuelTime > 0 && hasIngredients) {
            fuelTime--;
            progress++;
            setChanged();

            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
        } else  {
            progress = 0;
        }
    }

    private boolean hasRecipe() {
        ItemStack slot0 = inventory.getStackInSlot(0);
        ItemStack slot1 = inventory.getStackInSlot(1);
        ItemStack slot2 = inventory.getStackInSlot(2);
        ItemStack output = inventory.getStackInSlot(4);

        boolean hasFerrox = slot0.getItem() == ModItems.FERROX_POWDER.get();
        boolean hasObsidian = slot1.getItem() == ModItems.OBSIDIAN_POWDER.get();
        boolean hasPlatinum = slot2.getItem() == ModItems.PLATINUM_INGOT.get();


        boolean outputEmpty = output.isEmpty();
        boolean outputFits = output.getItem() == ModItems.FERROX_INGOT.get() && output.getCount() < output.getMaxStackSize();
        return hasFerrox && hasObsidian && hasPlatinum && (outputEmpty || outputFits);
    }

    private void craftItem() {
        ItemStack output = inventory.getStackInSlot(4);
        inventory.extractItem(0, 1, false);
        inventory.extractItem(1, 1, false);
        inventory.extractItem(2, 1, false);

        if (output.isEmpty()) {
            inventory.setStackInSlot(4, new ItemStack(ModItems.FERROX_INGOT.get(), 1));
        } else {
            output.grow(1);
        }
        setChanged();
    }
}
