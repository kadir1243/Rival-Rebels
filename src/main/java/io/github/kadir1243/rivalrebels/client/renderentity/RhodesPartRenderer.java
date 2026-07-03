package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesPiece;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.common.entity.RhodesType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Holder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

@OnlyIn(Dist.CLIENT)
public abstract class RhodesPartRenderer<T extends EntityRhodesPiece> extends EntityRenderer<T, RhodesPartRenderer.State> {
    private final QuadCollection model;

    public RhodesPartRenderer(EntityRendererProvider.Context context, StandaloneModelKey<QuadCollection> modelLocation) {
        super(context);
        model = Minecraft.getInstance().getModelManager().getStandaloneModel(modelLocation);
    }

    public Identifier getTextureLocation(State entity) {
        return entity.variant.value().getTexture();
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.scale(renderState.scale, renderState.scale, renderState.scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
        this.renderParts(renderState, poseStack, nodeCollector, renderState.lightCoords);
        poseStack.popPose();
    }

    public void renderParts(State entity, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight) {
        if (model != null) {
            ObjModels.submit(nodeCollector, RenderTypes.entitySolid(getTextureLocation(entity)), model, poseStack, entity.color, packedLight, OverlayTexture.NO_OVERLAY);
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
    public void extractRenderState(T entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.scale = entity.getScale();
        reusedState.variant = entity.getVariant();
        reusedState.color = entity.getColorRGBA();
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public float scale;
        public Holder<RhodesType> variant;
        public int color;
    }
}
