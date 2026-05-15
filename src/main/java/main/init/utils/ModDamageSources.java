package main.init.utils;

import net.minecraft.util.DamageSource;

public class ModDamageSources {

    public static final DamageSource BLEEDING = new BleedingDamage();

    public static final DamageSource FERROX_ACID = new FerroxAcidDamage();
}