package main.item;

import main.init.ModSounds;
import main.network.LanguageSyncPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Optional;

/*  **********************************
    I don't want to develop this code
    **********************************
 */

public class HeartItem extends Item {

    public HeartItem() {
        super(new Item.Properties()
                .tab(ItemGroup.TAB_MISC)
                .stacksTo(1)
        );
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClientSide) return ActionResult.pass(player.getItemInHand(hand));

        EntityRayTraceResult entityRay = getEntityRayTrace(player, world);

        if (entityRay != null && entityRay.getEntity() instanceof IronGolemEntity) {

            // Gets the player's language from the server
            String lang = LanguageSyncPacket.playerLanguages
                    .getOrDefault(player.getUUID(), "en_us");

            SoundEvent sound = lang.equals("ru_ru")
                    ? ModSounds.IRON_GOLEM_RU.get()
                    : ModSounds.IRON_GOLEM_EN.get();

            player.playSound(sound, 1.0f, 1.0f);

            player.sendMessage(
                    new TranslationTextComponent("item.frs.heart_item.golem"),
                    player.getUUID()
            );
        }

        return ActionResult.success(player.getItemInHand(hand));
    }

    private EntityRayTraceResult getEntityRayTrace(PlayerEntity player, World world) {
        Vector3d start = player.getEyePosition(1.0f);
        Vector3d look = player.getLookAngle();
        Vector3d end = start.add(look.x * 10, look.y * 10, look.z * 10);

        for (Entity entity : world.getEntities(player,
                player.getBoundingBox()
                        .expandTowards(look.x * 10, look.y * 10, look.z * 10)
                        .inflate(1.0))) {

            AxisAlignedBB bb = entity.getBoundingBox().inflate(0.3);
            Optional<Vector3d> hit = bb.clip(start, end);
            if (hit.isPresent()) {
                return new EntityRayTraceResult(entity, hit.get());
            }
        }
        return null;
    }
}