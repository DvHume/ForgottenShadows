package main.tile;

import main.container.ChemistryTableContainer;
import main.init.ModItems;
import main.init.ModTiles;
import main.init.abstractclass.AbstractBatteryItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChemistryTableTile extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public static final List<ChemistryRecipe> RECIPES = new ArrayList<>();

    public int progress = 0;
    public final int maxProgress = 200;

    // I was too lazy to change the names
    public int fuel = 0;          // Current energy
    public int maxFuel = 50_000;   // Max energy
    public boolean isCrafting = false;

    // array for automatic transfer of progress to the client
    public final IIntArray dataAccess = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return ChemistryTableTile.this.progress;
                case 1: return ChemistryTableTile.this.maxProgress;
                case 2: return ChemistryTableTile.this.fuel;
                case 3: return ChemistryTableTile.this.maxFuel;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: ChemistryTableTile.this.progress = value; break;
                case 2: ChemistryTableTile.this.fuel = value; break;
            }
        }

        @Override
        public int getCount() {return 4;}
    };

    public final ItemStackHandler inventory = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {setChanged();}
    };

    public ChemistryTableTile() {
        super(ModTiles.CHEMISTRY_TABLE.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.frs.chemistry_table");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new ChemistryTableContainer(id, playerInventory, this, this.dataAccess);
    }

    // the method is called when a button is clicked
    public void startCraft() {
        ChemistryRecipe recipe = getRecipe();
        if (recipe != null && HavelAndCanOutput(recipe)) {
            this.isCrafting = true;
            setChanged();
        }
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        ItemStack fuelStack = inventory.getStackInSlot(6);

        if (this.fuel < this.maxFuel && fuelStack.getItem() instanceof AbstractBatteryItem) {
            AbstractBatteryItem battery = (AbstractBatteryItem) fuelStack.getItem();
            int bEnergy = battery.getEnergy(fuelStack);

            if (bEnergy > 0) {
                int spaceInTable = this.maxFuel - this.fuel; // How much space is free in the table buffer

                // Draw energy from the battery at 50k
                int transferAmount = Math.min(spaceInTable, bEnergy);

                this.fuel += transferAmount;
                battery.setEnergy(fuelStack, bEnergy - transferAmount);
                setChanged();
            }
        }

        // Energy consumption every tick
        if (isCrafting) {
            ChemistryRecipe recipe = getRecipe();
            if (recipe != null && HavelAndCanOutput(recipe)) {

                /* Calculation of energy consumption:
                 The recipe requires 20k energy in total. MaxProgress = 200
                 20000 / 200 = 100.
                 */
                int energyPerTick = 100;

                if (this.fuel >= energyPerTick) {
                    this.fuel -= energyPerTick; // Takes away some of the energy
                    progress++;
                    setChanged();

                    if (progress >= maxProgress) {
                        inventory.extractItem(0, recipe.inputs[0].getCount(), false);
                        inventory.extractItem(1, recipe.inputs[1].getCount(), false);
                        inventory.extractItem(2, recipe.inputs[2].getCount(), false);

                        inventory.insertItem(3, recipe.output1.copy(), false);
                        inventory.insertItem(4, recipe.output2.copy(), false);
                        inventory.insertItem(5, recipe.output3.copy(), false);

                        progress = 0;
                        isCrafting = false;
                        setChanged();
                    }
                } else {
                    // if the energy runs out, the craft pauses, saving the current progress
                    setChanged();
                }
            } else {
                // if you remove items in the middle of crafting, it resets
                progress = 0;
                isCrafting = false;
                setChanged();
            }
        }
    }

    private ChemistryRecipe getRecipe() {
        for (ChemistryRecipe recipe : RECIPES) {
            if (recipe.matches(inventory.getStackInSlot(0), inventory.getStackInSlot(1), inventory.getStackInSlot(2))) {
                return recipe;
            }
        }
        return null;
    }

    private boolean HavelAndCanOutput(ChemistryRecipe recipe) {
        return canInsert(3, recipe.output1) && canInsert(4, recipe.output2) && canInsert(5, recipe.output3);
    }

    private boolean canInsert(int slot, ItemStack result) {
        if (result == null || result.isEmpty()) return true;
        ItemStack current = inventory.getStackInSlot(slot);
        if (current.isEmpty()) return true;
        if (!current.sameItem(result)) return false;
        return current.getCount() + result.getCount() <= current.getMaxStackSize();
    }

    @Override
    public void load(net.minecraft.block.BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("Progress");
        this.fuel = nbt.getInt("Fuel");
        this.isCrafting = nbt.getBoolean("IsCrafting");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("Progress", this.progress);
        nbt.putInt("Fuel", this.fuel);
        nbt.putBoolean("IsCrafting", this.isCrafting);
        return super.save(nbt);
    }

    static {
        addRecipe(ModItems.SULFUR.get(), 3, ModItems.FERROX_POWDER.get(), 1, Items.GLOWSTONE_DUST, 3, new ItemStack(ModItems.FERROX_ACID_TUBE.get()), ItemStack.EMPTY, ItemStack.EMPTY);
    }

    public static void addRecipe(Item i1, int c1, Item i2, int c2, Item i3, int c3, ItemStack o1, ItemStack o2, ItemStack o3) {
        RECIPES.add(new ChemistryRecipe(new ItemStack(i1, c1),
                new ItemStack(i2, c2),
                new ItemStack(i3, c3), o1, o2, o3));
    }

    public static class ChemistryRecipe {
        public final ItemStack[] inputs;
        public final ItemStack output1;
        public final ItemStack output2;
        public final ItemStack output3;

        public ChemistryRecipe(ItemStack i1, ItemStack i2, ItemStack i3, ItemStack o1, ItemStack o2, ItemStack o3) {
            this.inputs = new ItemStack[]{i1, i2, i3};
            this.output1 = o1;
            this.output2 = o2;
            this.output3 = o3;
        }

        public boolean matches(ItemStack s1, ItemStack s2, ItemStack s3) {
            return s1.getItem() == inputs[0].getItem() && s1.getCount() >= inputs[0].getCount() &&
                    s2.getItem() == inputs[1].getItem() && s2.getCount() >= inputs[1].getCount() &&
                    s3.getItem() == inputs[2].getItem() && s3.getCount() >= inputs[2].getCount();
        }
    }
}