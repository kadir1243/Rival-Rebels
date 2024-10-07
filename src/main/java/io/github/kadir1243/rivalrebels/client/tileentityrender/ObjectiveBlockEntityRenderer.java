package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.client.model.ModelObjective;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.tileentity.AbstractObjectiveBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

@OnlyIn(Dist.CLIENT)
public abstract class ObjectiveBlockEntityRenderer<T extends AbstractObjectiveBlockEntity> implements BlockEntityRenderer<T> {
    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entitySolid(getTexture()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        ModelObjective.renderA(poseStack, bufferSource.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(getTexture())), packedLight, packedOverlay);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f, packedLight, packedOverlay);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f, packedLight, packedOverlay);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f, packedLight, packedOverlay);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f, packedLight, packedOverlay);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f, packedLight, packedOverlay);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        ModelObjective.renderB(poseStack, buffer, (float) blockEntity.slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f, packedLight, packedOverlay);
        poseStack.popPose();
    }

    public abstract ResourceLocation getTexture();

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(AbstractObjectiveBlockEntity blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }
}
