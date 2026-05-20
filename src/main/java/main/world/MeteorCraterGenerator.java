package main.world;

import main.block.MeteorMagmaBlock;
import main.block.MeteorRockBlock;
import main.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public class MeteorCraterGenerator {

    public static void generate(World world, BlockPos impactPos, Random rand) {
        // Funnel parameter (crater)
        int craterRadius = 7 + rand.nextInt(3);

        // Meteor parameters
        int meteorRadius = 4;
        // Lower the center of the meteorite 3 blocks below the impact point
        BlockPos meteorCenter = impactPos.below(3);

        int scanRadius = Math.max(craterRadius, meteorRadius + 3);

        for (int x = -scanRadius; x <= scanRadius; x++) {
            for (int z = -scanRadius; z <= scanRadius; z++) {
                for (int y = -scanRadius; y <= scanRadius; y++) {

                    BlockPos currentPos = impactPos.offset(x, y, z);

                    // Distance to the explosion point(for the crater)
                    double distToImpact = Math.sqrt(x*x + y*y + z*z);
                    // Distance to the center of the meteorite(for the core)
                    double distToMeteor = Math.sqrt(x*x + (y + 3)*(y + 3) + z*z);

                    if (distToImpact < craterRadius) {
                        if (currentPos.getY() > impactPos.getY() - 3) {
                            world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
                            if (distToMeteor >= meteorRadius) continue;
                        }
                    }

                    if (distToMeteor < meteorRadius) {
                        // checking
                        if (distToMeteor < meteorRadius - 0.8) {
                            double chance = rand.nextDouble();

                            if (chance < 0.25) {
                                world.setBlock(currentPos, ModBlocks.METEOR_MAGMA.get().defaultBlockState()
                                        .setValue(MeteorMagmaBlock.NATURAL, true), 3);
                            } else if (chance < 0.50) {
                                world.setBlock(currentPos, getRandomOre(rand), 3);
                            } else {
                                // Hot rock(like you)
                                world.setBlock(currentPos, ModBlocks.METEOR_ROCK.get().defaultBlockState()
                                        .setValue(MeteorRockBlock.HOT, true), 3);
                            }
                        } else {
                            // The outer shell of a meteorite
                            world.setBlock(currentPos, ModBlocks.METEOR_ROCK.get().defaultBlockState()
                                    .setValue(MeteorRockBlock.HOT, true), 3);
                        }
                    }

                    else if (distToImpact < craterRadius && currentPos.getY() <= impactPos.getY() - 3) {
                        if (rand.nextFloat() < 0.4f) {
                            world.setBlock(currentPos, ModBlocks.METEOR_ROCK.get().defaultBlockState()
                                    .setValue(MeteorRockBlock.HOT, true), 3);
                        }
                    }
                }
            }
        }
    }

    public static BlockState getRandomOre(Random rand) {
        int roll = rand.nextInt(100);
        if (roll < 30) return ModBlocks.METEOR_IRON_ORE.get().defaultBlockState();
        if (roll < 55) return ModBlocks.METEOR_GOLD_ORE.get().defaultBlockState();
        if (roll < 70) return ModBlocks.PLATINUM_ORE.get().defaultBlockState();
        if (roll < 82) return ModBlocks.IRIDIUM_ORE.get().defaultBlockState();
        if (roll < 92) return ModBlocks.OSMIUM_ORE.get().defaultBlockState();
        // If nothing falls out, return the Rock
        return ModBlocks.METEOR_ROCK.get().defaultBlockState().setValue(MeteorRockBlock.HOT, true);
    }
}