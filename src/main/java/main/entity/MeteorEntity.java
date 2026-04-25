package main.entity;

import main.init.ModEntities;
import main.init.ModSounds;
import main.world.MeteorCraterGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;


public class MeteorEntity extends Entity {

    public MeteorEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noPhysics = false;
    }

    public MeteorEntity(World world, double x, double y, double z) {
        this(ModEntities.METEOR.get(), world);
        this.setPos(x, y, z);

        this.setDeltaMovement(
                (random.nextDouble() - 0.5) * 0.3, -1.5,
                (random.nextDouble() - 0.5) * 0.3
        );
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            for (int i = 0; i < 10; i++) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                        getX(), getY(), getZ(),
                        random.nextDouble() - 0.5,
                        random.nextDouble() - 0.5,
                        random.nextDouble() - 0.5);
                level.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0, 0.1, 0);
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            return;
        }

        if (tickCount % 40 == 0) {
            level.playSound(null, getX(), getY(), getZ(),
                    ModSounds.METEOR_FLY.get(),
                    SoundCategory.AMBIENT, 50.0f, 0.5f);
        }

        Vector3d motion = this.getDeltaMovement();
        double gravity = -0.02;
        double maxFallSpeed = -0.8;

        double newY = motion.y + gravity;
        if (newY < maxFallSpeed) newY = maxFallSpeed;
        this.setDeltaMovement(motion.x, motion.y - 0.05, motion.z);
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.onGround || this.horizontalCollision || this.getY() <= 0) {
            this.onImpact();
        }
    }

    private void onImpact() {
        if (level.isClientSide) return;

        level.playSound(null, getX(), getY() + 1.0D, getZ(),
                ModSounds.METEOR_IMPACT.get(),
                SoundCategory.AMBIENT, 10.0f, 1.0f);

        level.explode(this, getX(), getY(), getZ(), 3.0f, false,
                Explosion.Mode.DESTROY);

        MeteorCraterGenerator.generate(
                this.level, this.blockPosition(), this.random);
        this.remove();
    }

    @Override
    protected void defineSynchedData() {}
    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {}
    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {}

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}


