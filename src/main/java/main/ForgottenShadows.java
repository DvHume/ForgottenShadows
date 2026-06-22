package main;

import main.client.render.MeteorRenderer;
import main.init.*;
import main.init.ModNetwork;
import main.recipe.ModRecipes;
import main.screen.AlloyFurnaceScreen;
import main.screen.BatteryBoxScreen;
import main.screen.ChemistryTableScreen;
import main.screen.LeadContainerScreen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("frs")
public class ForgottenShadows
{
    private static final Logger LOGGER = LogManager.getLogger();

    public ForgottenShadows() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, main.config.ModConfig.SPEC, "frs-common.toml");
        ModEffects.EFFECTS.register(bus);
        ModItems.ITEMS.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModFluids.FLUIDS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModTiles.TILES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        ModRecipes.register(bus);
        ModEntities.ENTITIES.register(bus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModNetwork.register();
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        ScreenManager.register(ModContainers.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        ScreenManager.register(ModContainers.CHEMISTRY_TABLE.get(), ChemistryTableScreen::new);
        ScreenManager.register(ModContainers.BATTERY_BOX.get(), BatteryBoxScreen::new);
        ScreenManager.register(ModContainers.LEAD_CONTAINER.get(), LeadContainerScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.METEOR.get(), MeteorRenderer::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.SHEPHERDS_PURSE.get(), RenderType.cutout());

        RenderingRegistry.registerEntityRenderingHandler(
                ModEntities.ROTTEN_EGG.get(),
                manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer())
        );

        RenderTypeLookup.setRenderLayer(ModFluids.FERROX_ACID.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModFluids.FERROX_ACID_FLOWING.get(), RenderType.translucent());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("HELLO from Register Block");
        }
    }
}
