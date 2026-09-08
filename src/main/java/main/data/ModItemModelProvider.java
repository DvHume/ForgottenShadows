package main.data;

import main.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // ==== HERE ====
        generated(ModItems.GREEN_BATTERY);
    }


    private void blockItem(RegistryObject<Block> block) {
        getBuilder(block.getId().getPath())
                .parent(getExistingFile(modLoc("block/" + block.getId().getPath())));
    }

    private void generated(RegistryObject<? extends Item> item) {
        withExistingParent(item.getId().getPath(), "item/generated")
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void handheld(RegistryObject<? extends Item> item) {
        withExistingParent(item.getId().getPath(), "item/handheld")
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }

    private void spawnEgg(RegistryObject<? extends Item> item) {
        withExistingParent(item.getId().getPath(), "item/template_spawn_egg");
    }
}