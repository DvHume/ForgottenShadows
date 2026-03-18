/*package main.handler;
 WARNING:
 I stopped maintaining this code because I couldn't do what I wanted to do to the end, maybe I didn't have enough skills
 At first I wanted to make  him lose consciousness from bleeding, but  I didn't calculate te possibilities and I was just tired of this shit

Use this and UnconsciousOverlay files

Everything is in your hands. Thanks!

import main.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class UnconsciousHandler {
    //HP threshold at which the player loses consciousness
    private static final float UNCONSCIOUS_THRESHOLD = 2.0f;
    //How many ticks a player is unconscious
    static final int UNCONSCIOUS_DURATION = 200;
    //Do I need to write something here?
    private static final int TELEPORT_RADIUS = 500;

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        //We check that this is a player and not some mob
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        System.out.println("Это игрок получил урон! HP: " + event.getEntityLiving().getHealth());

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        //count HP after damage
        float healthAfterDamage = player.getHealth() - event.getAmount();
        /*We check that the player is not yet unconscious - otherwise there will be an endless loop.
        You don't want this, do you?
        CompoundNBT nbt = player.getPersistentData();
        if (nbt.getBoolean("isUnconscious")) {
            event.setCanceled(true);
            return;
        }

        //If HP drops to the threshold (let me remind you that this is 2 hearts) - we trigger loss of consciousness
        if (healthAfterDamage <= UNCONSCIOUS_THRESHOLD) {
            //cancel the damage
            event.setCanceled(true);
            //Sets HP to minimum
            player.setHealth(1.0f);

            //record in the NBT that the player is unconscious.Verification is required only through NBT
            nbt.putBoolean("isUnconscious", true);
            nbt.putLong("unconsciousTime", player.level.getGameTime());
            //random teleportation
            teleportRandom(player);
            //remove bleeding
            player.removeEffect(ModEffects.BLEEDING.get());
            //Consequences after waking up
            player.addEffect(new EffectInstance(ModEffects.HEAVY_WOUND.get(), 600, 0, false,false));
            player.addEffect(new EffectInstance(Effects.WEAKNESS, 600, 0, false,false));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 600, 0, false,false));

        }
    }

    private static void teleportRandom(PlayerEntity player) {
        World world = player.level;
        /*Generating random coordinates within a radius
        nextInt(TELEPORT_RADIUS * 2) - gives a number from 0 to 999
        subtract TELEPORT_RADIUS to get from -500 to 500

        int offsetX = world.random.nextInt(TELEPORT_RADIUS * 2) - TELEPORT_RADIUS;
        int offsetZ = world.random.nextInt(TELEPORT_RADIUS * 2) - TELEPORT_RADIUS;

        //We are looking for a safe height - the top block at these coordinates
        //getHeight returns the Y of the first unfilled block at the top
        double newX = player.getX() + offsetX;
        double newZ = player.getZ() + offsetZ;

        int newY = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) newX, (int) newZ);
        player.teleportTo(newX, newY, newZ);
    }
}
*/