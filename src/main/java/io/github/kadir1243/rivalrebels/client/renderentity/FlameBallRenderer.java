package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.entity.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.common.entity.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public abstract class FlameBallRenderer<T extends FlameBallProjectile> extends EntityRenderer<T> {
    public FlameBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack pose, MultiBufferSource bufferSource, int packedLight) {
        if (entity.tickCount < 3) return;
        pose.pushPose();

        pose.pushPose();
        float X = (entity.sequence % 4) / 4f;
        float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
        float size = getSize(entity);
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(RRIdentifiers.etflamebluered));
        pose.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pose.mulPose(Axis.XP.rotationDegrees(90));
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(entity.rotation));
        buffer.addVertex(pose.last(), -size, 0, -size).setColor(CommonColors.WHITE).setUv(X, Y).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose.last(), 0, 1, 0);
        buffer.addVertex(pose.last(),  size, 0, -size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose.last(), 0, 1, 0);
        buffer.addVertex(pose.last(),  size, 0,  size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y + 0.25f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose.last(), 0, 1, 0);
        buffer.addVertex(pose.last(), -size, 0,  size).setColor(CommonColors.WHITE).setUv(X, Y + 0.25f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose.last(), 0, 1, 0);
        pose.popPose();
        pose.popPose();

        pose.popPose();
    }

    public abstract float getSize(T entity);

    @Override
    public ResourceLocation getTextureLocation(T entity) {
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
}
