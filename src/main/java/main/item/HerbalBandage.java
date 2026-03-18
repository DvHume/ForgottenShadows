package main.item;

import main.init.ModEffects;
import main.init.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.potion.Effect;

public class HerbalBandage extends Item {

    public HerbalBandage() {
        super(new Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).durability(5));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.pass(stack);
        }
        //если эффекта нет
        if (!player.hasEffect(ModEffects.BLEEDING.get())) {
            player.displayClientMessage(new TranslationTextComponent("message.frs.herbal_bandage").withStyle(TextFormatting.RED), true);
            return ActionResult.fail(stack);
        }


        EffectInstance current = player.getActiveEffectsMap().get(ModEffects.BLEEDING.get());
        int currentAmplifier = current.getAmplifier();
        int currentDuration = current.getDuration();

        player.removeEffect(ModEffects.BLEEDING.get());

        world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HERBAL_BANDAGE_USE.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);

        if (currentAmplifier > 0) {
            player.addEffect(new EffectInstance(ModEffects.BLEEDING.get(), currentDuration, currentAmplifier - 1, false, false));
            player.displayClientMessage(new TranslationTextComponent("message.frs.herbal_bandage2").withStyle(TextFormatting.GREEN), true);
        } else {
            player.displayClientMessage(new TranslationTextComponent("message.frs.herbal_bandage3").withStyle(TextFormatting.GREEN), true);
        }

        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
        return ActionResult.success(stack);
    }
}
