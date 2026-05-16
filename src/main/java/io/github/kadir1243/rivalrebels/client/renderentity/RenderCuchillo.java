/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package io.github.kadir1243.rivalrebels.client.renderentity;

import com.mojang.math.Transformation;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.entity.EntityCuchillo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.neoforged.neoforge.client.model.pipeline.TransformingVertexPipeline;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class RenderCuchillo extends EntityRenderer<EntityCuchillo, RenderCuchillo.State> {

    public RenderCuchillo(EntityRendererProvider.Context context) {
        super(context);
    }

    private static final Supplier<List<QuadHelper.BakedQuadWrapper>> BAKED_MODEL = QuadHelper.createQuads(Sheets.BLOCKS_MAPPER.apply(RRIdentifiers.etknife), buffer -> {
        byte var11 = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (0 + var11 * 10) / 32.0F;
        float var15 = (5 + var11 * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = 0.15625F;
        float var18 = (5 + var11 * 10) / 32.0F;
        float var19 = (10 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        QuadHelper.addFace(buffer,
            new Vector3f(-7, -2, -2),
            new Vector3f(-7, -2, 2),
            new Vector3f(-7, 2, 2),
            new Vector3f(-7, 2, -2),
            new TextureVertice(var16, var18),
            new TextureVertice(var17, var18),
            new TextureVertice(var17, var19),
            new TextureVertice(var16, var19));

        QuadHelper.addFace(buffer,
            new Vector3f(-7,  2, -2),
            new Vector3f(-7,  2,  2),
            new Vector3f(-7, -2,  2),
            new Vector3f(-7, -2, -2),
            new TextureVertice(var16, var18),
            new TextureVertice(var17, var18),
            new TextureVertice(var17, var19),
            new TextureVertice(var16, var19));

        for (int i = 0; i < 4; ++i) {
            Transformation transformation = new Transformation(null, Axis.XP.rotationDegrees(90 * (i + 1)), null, null);
            QuadHelper.addFace(buffer, transformation,
                new Vector3f(-8, -2, 0),
                new Vector3f( 8, -2, 0),
                new Vector3f( 8,  2, 0),
                new Vector3f(-8,  2, 0),
                new TextureVertice(var12, var14),
                new TextureVertice(var13, var14),
                new TextureVertice(var13, var15),
                new TextureVertice(var12, var15));
        }
    });

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
		float var20 = 0.05625F;

        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
		poseStack.scale(var20, var20, var20);
		poseStack.translate(-4.0F, 0.0F, 0.0F);
        QuadHelper.submitQuadSupplier(nodeCollector, poseStack, RenderTypes.entityCutout(RRIdentifiers.etknife), BAKED_MODEL, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
	}


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityCuchillo p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
    }
}
