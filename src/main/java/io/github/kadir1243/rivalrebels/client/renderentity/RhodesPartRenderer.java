package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesPiece;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.Holder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

@OnlyIn(Dist.CLIENT)
public abstract class RhodesPartRenderer<T extends EntityRhodesPiece> extends EntityRenderer<T, RhodesPartRenderer.State> {
    private final QuadCollection model;

    public RhodesPartRenderer(EntityRendererProvider.Context context, StandaloneModelKey<QuadCollection> modelLocation) {
        super(context);
        model = context.getModelManager().getStandaloneModel(modelLocation);
    }

    public ResourceLocation getTextureLocation(State entity) {
        return entity.variant.value().getTexture();
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(renderState.scale, renderState.scale, renderState.scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
        this.renderParts(renderState, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    public void renderParts(State entity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (model != null) {
            ObjModels.render(model, bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), poseStack, entity.color, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(T p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.scale = p_entity.getScale();
        reusedState.variant = p_entity.getVariant();
        reusedState.color = p_entity.getColorRGBA();
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public float scale;
        public Holder<RhodesType> variant;
        public int color;
    }
}
