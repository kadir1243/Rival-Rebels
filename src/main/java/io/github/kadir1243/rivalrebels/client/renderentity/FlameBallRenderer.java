package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.entity.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.LightCoordsUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public abstract class FlameBallRenderer<T extends FlameBallProjectile> extends EntityRenderer<T, FlameBallRenderer.State> {
    public FlameBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State renderState, PoseStack pose, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.ageInTicks < 3) return;
        pose.pushPose();

        pose.pushPose();
        float X = (renderState.sequence % 4) / 4f;
        float Y = (renderState.sequence - (renderState.sequence % 4)) / 16f;
        float size = getSize(renderState);
        pose.mulPose(cameraRenderState.orientation);
        pose.mulPose(Axis.XP.rotationDegrees(90));
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
        nodeCollector.submitCustomGeometry(pose, RenderTypes.entityTranslucentEmissive(RRIdentifiers.etflamebluered), (pose1, buffer) -> {
            buffer.addVertex(pose1, -size, 0, -size).setColor(CommonColors.WHITE).setUv(X, Y).setLight(LightCoordsUtil.FULL_BRIGHT).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose.last(), 0, 1, 0);
            buffer.addVertex(pose1,  size, 0, -size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y).setLight(LightCoordsUtil.FULL_BRIGHT).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose.last(), 0, 1, 0);
            buffer.addVertex(pose1,  size, 0,  size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y + 0.25f).setLight(LightCoordsUtil.FULL_BRIGHT).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose.last(), 0, 1, 0);
            buffer.addVertex(pose1, -size, 0,  size).setColor(CommonColors.WHITE).setUv(X, Y + 0.25f).setLight(LightCoordsUtil.FULL_BRIGHT).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose.last(), 0, 1, 0);
        });
        pose.popPose();
        pose.popPose();

        pose.popPose();
    }

    @Override
    public void extractRenderState(T entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.sequence = entity.sequence;
        reusedState.rotation = entity.rotation;
    }

    public abstract float getSize(EntityRenderState entity);

    public Identifier getTextureLocation(T entity) {
        if (entity instanceof EntityFlameBall1) return RRIdentifiers.etflamebluered;
        if (entity instanceof EntityFlameBall2) return RRIdentifiers.etflameblue;
        if (entity instanceof EntityFlameBall) return RRIdentifiers.etflameball;
        if (entity instanceof EntityFlameBallGreen) return RRIdentifiers.etflameballgreen;
        return null;
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }

    public static class State extends EntityRenderState {
        public int sequence;
        public float rotation;
    }
}
