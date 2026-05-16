package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.client.model.ModelObjective;
import io.github.kadir1243.rivalrebels.common.tileentity.AbstractObjectiveBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class ObjectiveBlockEntityRenderer<T extends AbstractObjectiveBlockEntity> implements BlockEntityRenderer<T, ObjectiveBlockEntityRenderer.ObjectiveBlockEntityRenderState> {
    @Override
    public void submit(ObjectiveBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        float slide = renderState.slide;
        Identifier texture = getTexture();
        RenderType renderType = RenderTypes.entitySolid(texture);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        ModelObjective.renderA(nodeCollector, poseStack, texture, renderType, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 96f / 256f, 44f / 128f, 0.125f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 32f / 256f, 44f / 128f, 0.625f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 96f / 256f, 108f / 128f, 0.625f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 160f / 256f, 44f / 128f, 0.625f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 224f / 256f, 108f / 128f, 0.625f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        ModelObjective.renderB(poseStack, nodeCollector, renderType, slide, 224f / 256f, 44f / 128f, 0.625f, 0.84375f, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    public abstract Identifier getTexture();

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(AbstractObjectiveBlockEntity blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-1, -1, -1), blockEntity.getBlockPos().offset(2, 2, 2)));
    }

    @Override
    public ObjectiveBlockEntityRenderState createRenderState() {
        return new ObjectiveBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(T blockEntity, ObjectiveBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.slide = (float) blockEntity.slide;
    }

    public static class ObjectiveBlockEntityRenderState extends BlockEntityRenderState {
        public float slide;
    }
}
