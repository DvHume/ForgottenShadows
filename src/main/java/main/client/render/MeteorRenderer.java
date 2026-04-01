package main.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import main.client.model.MeteorModel;
import main.entity.MeteorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class MeteorRenderer extends EntityRenderer<MeteorEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("frs", "textures/entity/meteor.png");
    private final MeteorModel<MeteorEntity> model = new MeteorModel<>();

    public MeteorRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 10.F;
    }

    @Override
    public void render(MeteorEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        try {

            matrixStack.scale(2.5F, 2.5F, 2.5F);
            matrixStack.translate(0.0D, 0.5D, 0.0D);
            float age = (float) entity.tickCount + partialTicks;
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(age * 5.0f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(age * 2.0f));

            IVertexBuilder iVertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
            this.model.renderToBuffer(matrixStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 10.F);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            matrixStack.popPose();
        }
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MeteorEntity entity) {
        return TEXTURE;
    }
}