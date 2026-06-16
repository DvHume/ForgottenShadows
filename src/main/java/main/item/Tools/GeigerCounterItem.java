package main.item.Tools;

import main.init.ModBlocks;
import main.init.ModEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class GeigerCounterItem extends Item {
    public GeigerCounterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide &&  entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            boolean isHand = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
            if (isHand) {
                CompoundNBT nbt = player.getPersistentData();
                float internalDose = nbt.getFloat("RadiationDose");
                float externalRadiation = getExternalRadiation(player, world);
                float totalThreat = (internalDose * 0.15F) + externalRadiation;
                if (player.hasEffect(ModEffects.RADIATION.get()) || totalThreat > 0.01F) {
                    float clickChance = Math.max(0.08F, totalThreat * 0.7F);
                    if (clickChance > 0.85F) clickChance = 0.85F;

                    if (world.random.nextFloat() < clickChance) {
                        float randomPitch = 0.8F + world.random.nextFloat() * 0.4F;
                        float dynamicVolume = Math.min(0.7F, 0.2F + (totalThreat * 0.3F));
                        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, dynamicVolume, randomPitch);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            CompoundNBT nbt = player.getPersistentData();
            float internalDose = nbt.getFloat("RadiationDose");
            float externalRadiation = getExternalRadiation(player, world);
            TextFormatting intColor = TextFormatting.GREEN;
            if (internalDose >= 1.0F && internalDose < 4.0F) intColor = TextFormatting.YELLOW;
            if (internalDose >= 4.0F) intColor = TextFormatting.RED;
            TextFormatting extColor = TextFormatting.GREEN;
            if (externalRadiation >= 0.2F && externalRadiation < 0.6F) extColor = TextFormatting.YELLOW;
            if (externalRadiation >= 0.6F) extColor = TextFormatting.RED;
            String intDoseStr = String.format(intColor + "%.3f Rad/s" + TextFormatting.RESET, internalDose);
            String extRadStr = String.format(extColor + "%.3f Rad/s" + TextFormatting.RESET, externalRadiation);

            TextFormatting textColor = TextFormatting.GOLD;
            String message = String.format(
                    textColor + "[GEIGER] Body infection: %s\n" + textColor +"------\n" + "[GEIGER] Radiation in the air: %s",
                    intDoseStr, extRadStr
            );
            player.sendMessage(new StringTextComponent(message), player.getUUID());
        }
        return ActionResult.success(player.getItemInHand(hand));
    }

    private float getExternalRadiation(PlayerEntity player, World world) {
        float externalRadiation = 0.0F;

        BlockPos playerPos = player.blockPosition();
        int blockRadius = 2; // Проверяем область 5x5x5 вокруг игрока
        for (int x = -blockRadius; x <= blockRadius; x++) {
            for (int y = -blockRadius; y <= blockRadius; y++) {
                for (int z = -blockRadius; z <= blockRadius; z++) {
                    BlockPos targetPos = playerPos.offset(x, y, z);
                    BlockState state = world.getBlockState(targetPos);

                    // Если заражённый блок под ногами или рядом
                    if (state.is(ModBlocks.RADIOACTIVE_BLOCK.get())) {
                        // Считаем расстояние до блока
                        double distanceSq = player.distanceToSqr(targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5);
                        // Чем ближе блок к игроку тем сильнее фонит гейгер
                        externalRadiation += (float) (0.04D / Math.max(1.0D, distanceSq)); //Отрезаем совсем мелкие значения
                    }
                }
            }
        }

        AxisAlignedBB searchBox = player.getBoundingBox().inflate(5.0D);
        List<AreaEffectCloudEntity> cloudEntities = world.getEntitiesOfClass(AreaEffectCloudEntity.class, searchBox);
        for (AreaEffectCloudEntity cloud : cloudEntities) {
            boolean hasRadiation = false;
            CompoundNBT cloudNbt = new CompoundNBT();
            cloud.saveWithoutId(cloudNbt);
            if (cloudNbt.contains("Effects", 9)) {
                ListNBT effectsList = cloudNbt.getList("Effects", 10);
                for (int i = 0; i < effectsList.size(); i++) {
                    CompoundNBT effectTag = effectsList.getCompound(i);
                    int effectId = effectTag.getByte("Id");
                    if (effectId == Effect.getId(ModEffects.RADIATION.get())) {
                        hasRadiation = true;
                        break;
                    }
                }
            }
            if (!hasRadiation && cloudNbt.contains("Potion", 8)) {
                String potionName = cloudNbt.getString("Potion");
                if (potionName.contains("radiation")) {
                    hasRadiation = true;
                }
            }
            if (hasRadiation) {
                double distanceSq = player.distanceToSqr(cloud);
                externalRadiation += (float) (1.5D / Math.max(1.0D, distanceSq));
            }
        }
        return externalRadiation;
    }
}
