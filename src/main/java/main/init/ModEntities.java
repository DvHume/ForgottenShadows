package main.init;

import main.entity.MeteorEntity;
import main.entity.RottenEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, "frs");

    public static final RegistryObject<EntityType<MeteorEntity>> METEOR =
            ENTITIES.register("meteor", () ->
                    EntityType.Builder.<MeteorEntity>of(MeteorEntity::new, EntityClassification.MISC)
                            .sized(2.0f, 2.0f)
                            .build("meteor")
            );

    public static final RegistryObject<EntityType<RottenEggEntity>> ROTTEN_EGG =
            ENTITIES.register("rotten_egg", () ->
                    EntityType.Builder.<RottenEggEntity>of(RottenEggEntity::new, EntityClassification.MISC)
                            .sized(0.25f, 0.25f)
                            .build("rotten_egg")
            );

}