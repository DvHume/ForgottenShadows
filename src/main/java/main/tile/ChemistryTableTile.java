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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChemistryTableTile extends TileEntity implements INamedContainerProvider {

    private static final List<ChemistryRecipe> RECIPES = new ArrayList<>();

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
        return new ChemistryTableContainer(id, playerInventory, this);
    }

    public void craft() {
        if (canCraft()) {
            inventory.extractItem(0, 1, false);
            inventory.extractItem(1, 1, false);
            inventory.extractItem(2, 1, false);
            inventory.extractItem(3, 1, false);

            ItemStack result = new ItemStack(ModItems.TEST_TUBE.get());
            inventory.insertItem(4, result, false);

            setChanged();
        }
    }

    private boolean canCraft() {
        return true;
    }

    @Override
    public void load(net.minecraft.block.BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
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
            return s1.getItem() == inputs[0] && s2.getItem() == inputs[1] &&
                    s3.getItem() == inputs[3] && s4.getItem() == inputs[3];
        }
    }
}
