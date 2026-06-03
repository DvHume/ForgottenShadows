package main.recipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "frs");

    public static final RegistryObject<IRecipeSerializer<?>> FACTORY_SERIALIZER =
            SERIALIZER.register("factory_crafting_serializer", ModRecipeSerializer::new);

    public static IRecipeType<ModRecipe> FACTORY_TYPE;

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);

        FACTORY_TYPE = Registry.register(Registry.RECIPE_TYPE,
                new ResourceLocation("frs", "factory_crafting"), new IRecipeType<ModRecipe>() {
            @Override
                    public String toString() {
                return "factory_crafting";
            }
        });
    }
}
