package main.handler;

import main.config.ModConfig;
import main.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SunburnHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.level;

        if (world.isClientSide || event.phase != TickEvent.Phase.END) return;

        // Checks from the config
        if (!ModConfig.ENABLE_DESERT_SUNBURN.get()) return;

        BlockPos pos = player.blockPosition();
        Biome biome = world.getBiome(pos);

        // if the player is in the desert and the sky is clear
        boolean isInDesert = biome.getBiomeCategory() == Biome.Category.DESERT;
        boolean canSeeSky = world.canSeeSky(pos);
        boolean isDayTime = world.isDay();

        CompoundNBT playerData = player.getPersistentData();
        int currentHeat = playerData.getInt("DesertHeatTicks");

        if (isInDesert && canSeeSky && isDayTime) {
            // Helmet check
            ItemStack helmetStack = player.getItemBySlot(EquipmentSlotType.HEAD);
            boolean hasLeatherHelmet = false;
            boolean hasMetalHelmet = false;

            if (!helmetStack.isEmpty() && helmetStack.getItem() instanceof ArmorItem) {
                ArmorItem helmet = (ArmorItem) helmetStack.getItem();
                IArmorMaterial material = helmet.getMaterial();

                if (material == ArmorMaterial.LEATHER) {
                    hasLeatherHelmet = true; // Only leather saves
                }
                else if (material == ArmorMaterial.IRON
                || material == ArmorMaterial.GOLD
                || material == ArmorMaterial.DIAMOND
                || material == ArmorMaterial.NETHERITE) {
                    hasMetalHelmet = true; // Bro, you're melting!
                }
            }

            // Heating logic
            if (hasLeatherHelmet) {
                if (currentHeat > 0) currentHeat--;
            }
            else if (hasMetalHelmet) {
                // if you are NOT wearing a leather helmet on your head - x2 head burns
                currentHeat += 2;
            }
            else {
                // if the players is without a helmet, the head burns is normal (+1 tick)
                currentHeat++;
            }

            if (currentHeat >= 600) {
                currentHeat = 500;

                EffectInstance activeEffect = player.getEffect(ModEffects.SUNBURN.get());
                int currentAmp = activeEffect != null ? activeEffect.getAmplifier() : 0;
                int newAmp = Math.min(3, currentAmp + (activeEffect != null ? 1: 0));
                player.addEffect(new EffectInstance(ModEffects.SUNBURN.get(), 300, newAmp, false, false, true));
            }
        } else {

            if (currentHeat > 0) {
                currentHeat = Math.max(0, currentHeat - 2);
            }
        }

        playerData.putInt("DesertHeatTicks", currentHeat);
    }
}
