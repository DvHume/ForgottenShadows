/*package main.entity;

import main.init.ModEffects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class CrowEntity extends FlyingEntity {

    public CrowEntity(EntityType<? extends CrowEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void registerGoals() {
        // Атакует игрока или моба с кровотечением в радиусе
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0f));

        // Цели для атаки
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class, 10, false, false,
                entity -> entity instanceof LivingEntity &&
                        ((LivingEntity) entity).hasEffect(ModEffects.BLEEDING.get())
        ));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
}*/