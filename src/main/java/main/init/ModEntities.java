package main.init;

import main.entity.MeteorEntity;
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
}