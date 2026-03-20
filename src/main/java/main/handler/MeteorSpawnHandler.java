package main.handler;

import main.entity.MeteorEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

public class MeteorSpawnHandler {

    private static final int MIN_INTERVAL = 24000 * 2;
    private static final int MAX_INTERVAL = 24000 * 4;

    private static int nextMeteorTick = -1;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.world instanceof ServerWorld)) return;

        ServerWorld world = (ServerWorld) event.world;

        if (nextMeteorTick == -1) {
            nextMeteorTick = (int) world.getGameTime() + getRandomInterval(world.random);
        }

        if (world.getGameTime() < nextMeteorTick) return;

        spawnMeteor(world);

        nextMeteorTick = (int) world.getGameTime() + getRandomInterval(world.random);
    }
    private static void spawnMeteor(ServerWorld world) {
        List<ServerPlayerEntity> players = world.players();
        if (players.isEmpty()) return;

        ServerPlayerEntity target = players.get(world.random.nextInt(players.size()));

        Random rand = world.random;
        int offsetX = (100 + rand.nextInt(50)) * (rand.nextBoolean() ? 1 : -1);
        int offsetZ = (100 + rand.nextInt(50)) * (rand.nextBoolean() ? 1 : -1);

        double x = target.getX() + offsetX;
        double z = target.getZ() + offsetZ;
        //spawn in air
        double y = 300;

        MeteorEntity meteor = new MeteorEntity(world, x, y, z);
        world.addFreshEntity(meteor);
    }
    private static int getRandomInterval(Random rand) {
        return MIN_INTERVAL + rand.nextInt(MAX_INTERVAL - MIN_INTERVAL);
    }
}
