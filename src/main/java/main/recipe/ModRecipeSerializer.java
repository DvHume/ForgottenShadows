package main.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ModRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ModRecipe> {

    @Override
    public ModRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // Reads an array of inputs
        NonNullList<Ingredient> inputs = NonNullList.create();
        JsonArray inputsArray = JSONUtils.getAsJsonArray(json, "inputs");
        for (JsonElement element : inputsArray) {
            inputs.add(Ingredient.fromJson(element));
        }

        // Reads an array of outputs
        NonNullList<ItemStack> outputs = NonNullList.create();
        JsonArray resultsArray = JSONUtils.getAsJsonArray(json, "results");
        for (JsonElement element : resultsArray) {
            outputs.add(CraftingHelper.getItemStack(element.getAsJsonObject(), true));
        }
        int cookTime = JSONUtils.getAsInt(json, "cookTime", 200);
        int energyCoast = JSONUtils.getAsInt(json, "energyCoast", 0);
        return new ModRecipe(recipeId, inputs, outputs, cookTime, energyCoast);
    }

    @Override
    public ModRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        int inputSize = buffer.readVarInt();
        NonNullList<Ingredient> inputs = NonNullList.withSize(inputSize, Ingredient.EMPTY);
        for (int i = 0; i < inputSize; i++) {
            inputs.set(i, Ingredient.fromNetwork(buffer));
        }

        int outputsSize = buffer.readVarInt();
        NonNullList<ItemStack> outputs = NonNullList.withSize(outputsSize, ItemStack.EMPTY);
        for (int i = 0; i < outputsSize; i++) {
            outputs.set(i, buffer.readItem());
        }

        int cookTime = buffer.readVarInt();
        int energyCoast = buffer.readVarInt();
        return new ModRecipe(recipeId, inputs, outputs, cookTime, energyCoast);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, ModRecipe recipe) {
        buffer.writeVarInt(recipe.getInputs().size());
        for (Ingredient ingredient : recipe.getInputs()) {
            ingredient.toNetwork(buffer);
        }

        buffer.writeVarInt(recipe.getOutputs().size());
        for (ItemStack stack : recipe.getOutputs()) {
            buffer.writeItem(stack);
        }

        buffer.writeVarInt(recipe.getCookTime());
        buffer.writeVarInt(recipe.getEnergyCoast());
    }
}
