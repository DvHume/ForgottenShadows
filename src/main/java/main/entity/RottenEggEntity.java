package main.entity;

import main.init.ModEntities;
import main.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class RottenEggEntity extends ProjectileItemEntity {

    public RottenEggEntity(EntityType<? extends RottenEggEntity> type, World world) {
        super(type, world);
    }

    public RottenEggEntity(World world, LivingEntity thrower) {
        super(ModEntities.ROTTEN_EGG.get(), thrower, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ROTTEN_EGG.get();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);

        if (result.getEntity() instanceof VillagerEntity) {
            result.getEntity().hurt(DamageSource.MAGIC, Float.MAX_VALUE);
        } else if (result.getEntity() instanceof LivingEntity) {
            result.getEntity().hurt(DamageSource.MAGIC, 1.0f);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!level.isClientSide) {
            this.remove();
        }
    }
}