package main.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModRecipe implements IRecipe<IInventory> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final NonNullList<ItemStack> outputs;
    private final int cookTime;
    private final int energyCoast;

    public ModRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, NonNullList<ItemStack> outputs, int cookTime, int energyCoast) {
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
        this.cookTime = cookTime;
        this.energyCoast = energyCoast;
    }

    public NonNullList<Ingredient> getInputs() {return this.inputs;}

    public NonNullList<ItemStack> getOutputs() {return this.outputs;}

    public int getCookTime() {return this.cookTime;}
    public int getEnergyCoast() {return this.energyCoast;}

    @Override
    public boolean matches(IInventory inv, World world) {
        // if a recipe has more ingredients than slots in the machine, the recipe is not suitable
        if (this.inputs.size() > inv.getContainerSize()) return false;

        // Each ingredient must match its slot
        for (int i = 0; i < this.inputs.size(); i++) {
            if (!this.inputs.get(i).test(inv.getItem(i))) {
                return false; // if at least one item does not match, reset
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return this.outputs.isEmpty() ? ItemStack.EMPTY : this.outputs.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.outputs.isEmpty() ? ItemStack.EMPTY : this.outputs.get(0);
    }

    @Override
    public ResourceLocation getId() {return this.id;}

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.FACTORY_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.FACTORY_TYPE;
    }
}
