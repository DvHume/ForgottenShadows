package main.data;

import main.ForgottenShadows;
import main.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // ==== HERE ====
    }

    private void registerCubeAll(RegistryObject<Block> block) {
        simpleBlock(block.get(), models().cubeAll(
                block.getId().getPath(),
                modLoc("block/" + block.getId().getPath())
        ));
    }

    private void registerOrientable(RegistryObject<Block> block, String name) {
        ModelFile model = models().orientable(
                name,
                modLoc("block/" + name + "_side"),
                modLoc("block/" + name + "_front"),
                modLoc("block/" + name + "_top")
        );
        simpleBlock(block.get(), model);
    }

    private void registerSolarPanel(RegistryObject<Block> block) {
        ModelFile model = models().cubeBottomTop(
                block.getId().getPath(),
                modLoc("block/" + block.getId().getPath() + "_side"),
                modLoc("block/" + block.getId().getPath() + "_bottom"),
                modLoc("block/" + block.getId().getPath() + "_top")
        );
        simpleBlock(block.get(), model);
    }

    private void registerCross(RegistryObject<Block> block, String texture) {
        ModelFile model = models().cross(
                block.getId().getPath(),
                modLoc("block/" + texture)
        );
        simpleBlock(block.get(), model);
    }

    private void registerLog(RegistryObject<Block> block) {
        ModelFile model = models().cubeColumn(
                block.getId().getPath(),
                modLoc("block/" + block.getId().getPath() + "_side"),
                modLoc("block/" + block.getId().getPath() + "_top")
        );
        axisBlock((RotatedPillarBlock) block.get(), model, model);
    }

    private void registerFluid(RegistryObject<Block> block) {
        // Для жидкостей обычно не нужна модель. Вроде
        // simpleBlock(block.get(), models().getBuilder("air"));
    }
}