package main.data;

import main.ForgottenShadows;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ForgottenShadows.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModBlockStateProvider(generator, ForgottenShadows.MOD_ID, existingFileHelper));

        generator.addProvider(new ModItemModelProvider(generator, ForgottenShadows.MOD_ID, existingFileHelper));

        generator.addProvider(new ModLootTableProvider(generator));
    }
}