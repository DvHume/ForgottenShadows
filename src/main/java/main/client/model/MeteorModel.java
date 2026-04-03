package main.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import main.entity.MeteorEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MeteorModel<T extends MeteorEntity> extends EntityModel<T> {
    private final ModelRenderer group;
    private final ModelRenderer pivot_r1;

    public MeteorModel() {
        texWidth = 128;
        texHeight = 128;

        group = new ModelRenderer(this);
        group.setPos(3.1F, 15.0F, -1.7F);

        pivot_r1 = new ModelRenderer(this);
        pivot_r1.setPos(-2.5F, -0.2F, 1.7F);
        group.addChild(pivot_r1);
        setRotationAngle(pivot_r1, 0.4185F, -0.1274F, 0.4529F);
        pivot_r1.texOffs(0, 0).addBox(-5.6F, -5.0F, -5.0F, 11.0F, 11.0F, 11.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        group.xRot = ageInTicks * 0.1f;
        group.yRot = ageInTicks * 0.1f;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        group.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}