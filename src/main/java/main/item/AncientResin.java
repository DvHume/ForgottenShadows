package main.item;

import main.init.ModEffects;
import main.init.ModItemGroups;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AncientResin extends Item {

    private static final int MESSAGE_COUNT = 5;

    public AncientResin() {
        super(new Item.Properties()
                .tab(ModItemGroups.CONSUMABLES)
                .stacksTo(16));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) return ActionResult.pass(stack);
        stack.shrink(1);

        // random message
        int msgIndex = world.random.nextInt(MESSAGE_COUNT);
        player.sendMessage(
                new TranslationTextComponent("item.frs.ancient_resin.msg" + msgIndex),
                player.getUUID()
        );

        // random effect
        int roll = world.random.nextInt(8);
        switch (roll) {
            case 0:
                // nothing
                break;
            case 1:
                // burn
                player.setSecondsOnFire(5);
                break;
            case 2:
                // poison
                player.addEffect(new EffectInstance(Effects.POISON, 100, 0, false, true));
                break;
            case 3:
                // what?
                player.addEffect(new EffectInstance(Effects.LEVITATION, 60, 0, false, true));
                break;
            case 4:
                // regeneration — at least something good
                player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 1, false, true));
                break;
            case 5:
                // bleeding
                player.addEffect(new EffectInstance(ModEffects.BLEEDING.get(), 200, 0, false, true));
                break;
            case 6:
                // TNT BOOM
                TNTEntity tnt = new TNTEntity(
                        world,
                        player.getX(),
                        player.getY(),
                        player.getZ(), player
                );
                world.addFreshEntity(tnt);
                world.playSound(null, player.blockPosition(), SoundEvents.TNT_PRIMED, SoundCategory.PLAYERS, 1.0F, 1.0F);
                break;
            case 7:
                FallingBlockEntity anvil = new FallingBlockEntity(
                        world,
                        player.getX(),
                        player.getY() + 8 + world.random.nextInt(10),
                        player.getZ(), Blocks.ANVIL.defaultBlockState()
                );
                // So that the server does not delete the block immediately
                anvil.time = 1;
                // damage
                anvil.setHurtsEntities(true);
                // spawn
                world.addFreshEntity(anvil);
                break;
        }

        return ActionResult.success(stack);
    }
}