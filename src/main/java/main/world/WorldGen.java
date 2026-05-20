package main.world;

import main.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldGen {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getCategory() == Biome.Category.NETHER) {
            generateOre(event.getGeneration(),
                    OreFeatureConfig.FillerBlockType.NETHERRACK,
                    ModBlocks.BLOOD_QUARTZ_ORE.get().defaultBlockState(),
                    6,
                    10,
                    100,
                    8
            );
        }

        if (event.getCategory() == Biome.Category.SWAMP) {
            generateOre(event.getGeneration(),
                    OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ModBlocks.FERROX_ORE.get().defaultBlockState(),
                    5,
                    10,
                    30,
                    4
            );
        }
        //
        if (event.getCategory() == Biome.Category.NETHER ||
                event.getCategory() == Biome.Category.THEEND) return;

        generateOre(event.getGeneration(),
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.TIN_ORE.get().defaultBlockState(),
                8,
                20,
                50,
                8
        );

        if (event.getCategory() == Biome.Category.NETHER ||
                event.getCategory() == Biome.Category.THEEND) return;

        generateOre(event.getGeneration(),
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.NICKEL_ORE.get().defaultBlockState(),
                8,
                20,
                50,
                6
        );

        if (event.getCategory() != Biome.Category.NETHER &&
        event.getCategory() != Biome.Category.THEEND &&
        event.getCategory() != Biome.Category.DESERT &&
        event.getCategory() != Biome.Category.ICY &&
        event.getCategory() != Biome.Category.SWAMP) {

            // There were some problems while writing this crap, so look at this terrible code
            event.getGeneration().addFeature(
                    GenerationStage.Decoration.VEGETAL_DECORATION,
                    Feature.RANDOM_PATCH.configured(
                                    new BlockClusterFeatureConfig.Builder(
                                            new SimpleBlockStateProvider(
                                                    ModBlocks.SHEPHERDS_PURSE.get().defaultBlockState()
                                            ),
                                            SimpleBlockPlacer.INSTANCE
                                    )
                                            .tries(4)
                                            .build()
                            ).decorated(Placement.HEIGHTMAP_WORLD_SURFACE.configured(IPlacementConfig.NONE))
                            .squared()
                            .count(2)
            );
        }
    }

    private static void generateOre(BiomeGenerationSettingsBuilder gen,
                                    RuleTest filler,
                                    BlockState state,
                                    int veinSize, int minY, int maxY, int count) {
        gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(
                        new OreFeatureConfig(filler, state, veinSize)
                ).decorated(
                        Placement.RANGE.configured(
                                new TopSolidRangeConfig(minY, 0, maxY)
                        )
                ).squared().count(count)
        );
    }
}