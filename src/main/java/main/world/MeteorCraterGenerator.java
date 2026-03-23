package main.world;

import main.block.MeteorRockBlock;
import main.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MeteorCraterGenerator {

    public static void generate(World world, BlockPos center, Random rand) {
        int radius = 4 + rand.nextInt(3); //radius crater

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    BlockPos pos = center.offset(x, y, z);
                    double dist = Math.sqrt(x*x + y*y + z*z);

                    if (dist > radius) continue;
                    //core
                    if (dist < radius * 0.4) {
                        world.setBlock(pos, ModBlocks.METEOR_MAGMA.get().defaultBlockState(), 3);
                    }
                    //mid - hot meteor_rock + ore's
                    else if (dist < radius * 0.7) {
                        if (rand.nextInt(5) == 0) {
                            //random ore
                            world.setBlock(pos, getRandomOre(rand), 3);
                        } else {
                            world.setBlock(pos, ModBlocks.METEOR_ROCK.get()
                                    .defaultBlockState()
                                    .setValue(MeteorRockBlock.HOT, true), 3);
                        }
                    }
                    else {
                        world.setBlock(pos, ModBlocks.METEOR_ROCK.get()
                                .defaultBlockState(), 3);
                    }
                    if (y > 0 && dist < radius * 0.8) {
                        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private  static BlockState getRandomOre(Random rand) {
        int roll = rand.nextInt(100);

        if (roll < 30) return ModBlocks.METEOR_IRON_ORE.get().defaultBlockState();
        if (roll < 55) return ModBlocks.METEOR_GOLD_ORE.get().defaultBlockState();
        if (roll < 70) return ModBlocks.PLATINUM_ORE.get().defaultBlockState();
        if (roll < 82) return ModBlocks.IRIDIUM_ORE.get().defaultBlockState();
        if (roll < 92) return ModBlocks.OSMIUM_ORE.get().defaultBlockState();
        return ModBlocks.METEOR_ROCK.get().defaultBlockState();
    }
}
