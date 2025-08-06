package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelDisk;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RoddiskRenderer extends EntityRenderer<RoddiskBase, RoddiskRenderer.State> {
    private float er = 0;

    public RoddiskRenderer(EntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f + er));
        poseStack.scale(0.4f, 0.4f, 0.4f);
        poseStack.pushPose();

        RenderType buffer;
        if (renderState.isNoiseBuffer) buffer = RenderTypes.CELLULAR_NOISE;
        else buffer = RenderType.entitySolid(renderState.texture);
        ModelDisk.render(poseStack, bufferSource.getBuffer(buffer), packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(RoddiskBase livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(RoddiskBase entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(RoddiskBase p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.isNoiseBuffer = p_entity instanceof EntityRoddiskRep;
        reusedState.texture = switch (p_entity) {
            case EntityRoddiskRegular ignored -> RRIdentifiers.etdisk0;
            case EntityRoddiskRebel ignored -> RRIdentifiers.etdisk1;
            case EntityRoddiskOfficer ignored -> RRIdentifiers.etdisk2;
            case EntityRoddiskLeader ignored -> RRIdentifiers.etdisk3;
            default -> null;
        };
        er = Mth.lerp(partialTick, er, er + 13.46F);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public ResourceLocation texture;
        public boolean isNoiseBuffer;
    }
}
