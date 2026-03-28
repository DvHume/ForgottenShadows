package main.handler;

import main.entity.MeteorEntity;
import main.init.ModEntities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "frs")
public class MeteorSpawnHandler {

    private static final int MIN_INTERVAL = 36000;
    private static final int MAX_INTERVAL = 24000 * 2;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isClientSide) return;
        if (event.world.dimension() != net.minecraft.world.World.OVERWORLD) return;

        ServerWorld world = (ServerWorld) event.world;
        MeteorSavedData data = MeteorSavedData.get(world);

        if (data.nextMeteorTick <= 0) {
            data.nextMeteorTick = world.getGameTime() + getRandomInterval(world.random);
            data.setDirty();
        }

        if (world.getGameTime() >= data.nextMeteorTick) {
            spawnMeteor(world);
            data.nextMeteorTick = world.getGameTime() + getRandomInterval(world.random);
            data.setDirty();
        }
    }

    private static void spawnMeteor(ServerWorld world) {
        List<ServerPlayerEntity> players = world.players();
        if (players.isEmpty()) return;

        ServerPlayerEntity target = players.get(world.random.nextInt(players.size()));
        Random rand = world.random;

        int offsetX = (40 + rand.nextInt(30)) * (rand.nextBoolean() ? 1 : -1);
        int offsetZ = (40 + rand.nextInt(30)) * (rand.nextBoolean() ? 1 : -1);

        double x = target.getX() + offsetX;
        double z = target.getZ() + offsetZ;
        double y = 130;

        MeteorEntity meteor = new MeteorEntity(ModEntities.METEOR.get(), world);
        meteor.setPos(x, y, z);
        meteor.setDeltaMovement((rand.nextDouble() - 0.5) * 0.2, -0.4, (rand.nextDouble() - 0.5) * 0.2);

        world.addFreshEntity(meteor);
    }

    private static int getRandomInterval(Random rand) {
        return MIN_INTERVAL + rand.nextInt(MAX_INTERVAL - MIN_INTERVAL);
    }

    public static class MeteorSavedData extends WorldSavedData {
        private static final String DATA_NAME = "frs_meteor_data";
        public long nextMeteorTick = -1;

        public MeteorSavedData() {
            super(DATA_NAME);
        }

        @Override
        public void load(CompoundNBT nbt) {
            nextMeteorTick = nbt.getLong("nextTick");
        }

        @Override
        public CompoundNBT save(CompoundNBT nbt) {
            nbt.putLong("nextTick", nextMeteorTick);
            return nbt;
        }

        public static MeteorSavedData get(ServerWorld world) {
            DimensionSavedDataManager storage = world.getDataStorage();
            return storage.computeIfAbsent(MeteorSavedData::new, DATA_NAME);
        }
    }
}