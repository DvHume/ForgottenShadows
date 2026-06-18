package main.handler;

import main.init.ModEffects;
import main.init.ModItems;
import main.item.other.RadioactiveItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RadiationPlayerInventory {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level.isClientSide) {
            PlayerEntity player = event.player;

            if (player.tickCount % 20 == 0) {
                float maxSourceRadiation = 0.0F;
                float maxInfectedRad = 0.0F;
                boolean hasRadioactiveSource = false;

                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    ItemStack stack = player.inventory.getItem(i);
                    if (stack.isEmpty()) continue;

                    if (stack.getItem() == ModItems.LEAD_CONTAINER.get()) {
                        CompoundNBT containerNbt = stack.getTag();

                        if (containerNbt != null) {
                            float fluid = containerNbt.getFloat("FluidBuffer");
                            int damage = containerNbt.getInt("ContainerDamage");

                            if (fluid <= 0.0F && damage > 0) {
                                maxInfectedRad = Math.max(maxInfectedRad, 0.4F);
                                hasRadioactiveSource = true;
                            }
                        }
                        continue;
                    }

                    CompoundNBT nbt = stack.getTag();
                    if (stack.getItem() instanceof RadioactiveItem) {
                        RadioactiveItem radItem = (RadioactiveItem) stack.getItem();
                        float itemRad = radItem.getRadiationPerSec();
                        if (itemRad > maxSourceRadiation) {
                            maxSourceRadiation = itemRad;
                        }
                        hasRadioactiveSource = true;
                    } else if (nbt != null && nbt.contains("InfectedRad")) {
                        float infectedRad = nbt.getFloat("InfectedRad");
                        if (infectedRad > maxInfectedRad) {
                            maxInfectedRad = infectedRad;
                        }
                    }
                }

                if (hasRadioactiveSource) {
                    boolean inventoryChanged = false;
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack stack = player.inventory.getItem(i);
                        if (stack.isEmpty() || stack.getItem() instanceof RadioactiveItem) continue;
                        if (stack == player.getMainHandItem()|| stack == player.getOffhandItem()) continue;
                        CompoundNBT nbt = stack.getOrCreateTag();
                        float currentInfection = nbt.getFloat("InfectedRad");

                        float newInfection = currentInfection + 0.005F;
                        nbt.putFloat("InfectedRad", newInfection);
                        inventoryChanged = true;


                        if (stack.getItem().isEdible() && currentInfection >= 0.05F) {
                            int originalCount = stack.getCount();
                            int dustCount = originalCount > 2 ? (int) Math.round(originalCount * 0.6D) : 1;

                            ItemStack dustStack = new ItemStack(ModItems.RADIOACTIVE_DUST.get(), dustCount);
                            CompoundNBT dustNbt = dustStack.getOrCreateTag();
                            dustNbt.putFloat("InfectedRad", newInfection);
                            player.inventory.setItem(i, dustStack);
                        }
                    }

                    if (inventoryChanged && player.containerMenu != null) {
                        player.containerMenu.broadcastChanges();
                    }
                }

                float finalTickDose = 0.0F;

                if (hasRadioactiveSource) {
                    finalTickDose = maxSourceRadiation;
                } else if (maxInfectedRad > 0.0F) {
                    finalTickDose = maxInfectedRad * 0.3F;
                }

                if (finalTickDose > 0.0F) {
                    CompoundNBT playerNbt = player.getPersistentData();
                    float currentDose = playerNbt.getFloat("RadiationDose");
                    playerNbt.putFloat("RadiationDose", currentDose + finalTickDose);
                    player.addEffect(new EffectInstance(ModEffects.RADIATION.get(), 25, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = event.getItem();

            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.contains("InfectedRad")) {
                float infectedRad = nbt.getFloat("InfectedRad");

                if (infectedRad > 0.0F) {
                    CompoundNBT playerNbt = player.getPersistentData();
                    float currentDose = playerNbt.getFloat("RadiationDose");
                    float internalDose = infectedRad * 50.0F;
                    playerNbt.putFloat("RadiationDose", currentDose + internalDose);

                    player.addEffect(new EffectInstance(Effects.POISON, 150, 1));
                    player.addEffect(new EffectInstance(Effects.CONFUSION, 200, 0));
                }
            }
        }
    }
}
