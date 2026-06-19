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

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureFace;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.entity.EntityGoo;
import com.mojang.blaze3d.vertex.PoseStack;
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
import org.joml.Vector3f;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class RenderGoo extends EntityRenderer<EntityGoo, EntityRenderState> {
    public RenderGoo(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.ageInTicks < 2) return;
        poseStack.pushPose();
        poseStack.scale(0.25F, 0.25F, 0.25F);
        poseStack.mulPose(cameraRenderState.orientation);
        ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etgoo), BAKED_MODEL.get().quadCollection(), poseStack, CommonColors.WHITE, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(buffer -> {
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        QuadHelper.addFace(buffer, new Vector3f((0.0F - var8), (0.0F - var9), 0), new Vector3f((var7 - var8), (0.0F - var9), 0), new Vector3f((var7 - var8), (var7 - var9), 0), new Vector3f((0.0F - var8), (var7 - var9), 0), new TextureFace(new TextureVertice(0, 0), new TextureVertice(1, 0), new TextureVertice(1, 1), new TextureVertice(0, 1)));
    });
}
