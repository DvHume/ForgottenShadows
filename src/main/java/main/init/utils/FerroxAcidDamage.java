package main.init.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FerroxAcidDamage extends DamageSource {

    public FerroxAcidDamage() {
        super("ferrox_acid_damage");

        this.bypassArmor();
    }

    @Override
    public ITextComponent getLocalizedDeathMessage(LivingEntity entity) {
        String langKey = "death.attack." + this.msgId;
        return new TranslationTextComponent(langKey, entity.getDisplayName());
    }
}
