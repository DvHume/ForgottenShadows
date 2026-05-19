package main.tile;

import main.container.ChemistryTableContainer;
import main.init.ModItems;
import main.init.ModTiles;
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

    private static final List<ChemistryRecipe> RECIPES = new ArrayList<>();

    public int progress = 0;
    public final int maxProgress = 200;
    public boolean isCrafting = false;

    // array for automatic transfer of progress to the client
    public final IIntArray dataAccess = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return ChemistryTableTile.this.progress;
                case 1: return ChemistryTableTile.this.maxProgress;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: ChemistryTableTile.this.progress = value; break;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public final ItemStackHandler inventory = new ItemStackHandler(5) {
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
        if (recipe != null && canCraft(recipe.output)) {
            this.isCrafting = true;
            setChanged();
        }
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        if (isCrafting) {
            ChemistryRecipe recipe = getRecipe();
            if (recipe != null && canCraft(recipe.output)) {
                progress++;
                setChanged();

                if (progress >= maxProgress) {
                    inventory.extractItem(0, 1, false);
                    inventory.extractItem(1, 1, false);
                    inventory.extractItem(2, 1, false);
                    inventory.extractItem(3, 1, false);

                    inventory.insertItem(4, recipe.output.copy(), false);

                    progress = 0;
                    isCrafting = false;
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
            if (recipe.matches(inventory.getStackInSlot(0), inventory.getStackInSlot(1), inventory.getStackInSlot(2), inventory.getStackInSlot(3))) {
                return recipe;
            }
        }
        return null;
    }

    private boolean canCraft(ItemStack result) {
        ItemStack outputSlot = inventory.getStackInSlot(4);
        if (outputSlot.isEmpty()) return true;
        if (!outputSlot.sameItem(result)) return false;
        return outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize();
    }

    @Override
    public void load(net.minecraft.block.BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("Progress");
        this.isCrafting = nbt.getBoolean("IsCrafting");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("Progress", this.progress);
        nbt.putBoolean("IsCrafting", this.isCrafting);
        return super.save(nbt);
    }

    static {
        addRecipe(ModItems.SULFUR.get(), ModItems.FERROX_POWDER.get(), ModItems.TEST_TUBE.get(), Items.GLOWSTONE_DUST, new ItemStack(ModItems.FERROX_ACID_TUBE.get()));
    }

    private static void addRecipe(Item i1, Item i2, Item i3, Item i4, ItemStack result) {
        RECIPES.add(new ChemistryRecipe(i1, i2, i3, i4, result));
    }

    private static class ChemistryRecipe {
        private final Item[] inputs;
        private final ItemStack output;

        public ChemistryRecipe(Item i1, Item i2, Item i3, Item i4, ItemStack output) {
            this.inputs = new Item[]{i1, i2, i3, i4};
            this.output = output;
        }

        public boolean matches(ItemStack s1, ItemStack s2, ItemStack s3, ItemStack s4) {
            return s1.getItem() == inputs[0] &&
                    s2.getItem() == inputs[1] &&
                    s3.getItem() == inputs[2] &&
                    s4.getItem() == inputs[3];
        }
    }
}