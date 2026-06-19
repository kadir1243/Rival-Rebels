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
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.entity.EntityBlood;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Vector3f;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class RenderBlood extends EntityRenderer<EntityBlood, EntityRenderState> {
    public RenderBlood(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    public static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(buffer -> {
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        QuadHelper.addVertice(buffer, new Vector3f((0.0F - var8), (0.0F - var9), 0), new TextureVertice(0, 0));
        QuadHelper.addVertice(buffer, new Vector3f((var7 - var8), (0.0F - var9), 0), new TextureVertice(1, 0));
        QuadHelper.addVertice(buffer, new Vector3f((var7 - var8), (var7 - var9), 0), new TextureVertice(1, 1));
        QuadHelper.addVertice(buffer, new Vector3f((0.0F - var8), (var7 - var9), 0), new TextureVertice(0, 1));
    });

    @Override
    public void render(EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
		poseStack.scale(0.25F, 0.25F, 0.25F);
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etblood));
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        ModelBlockRenderer.renderModel(poseStack.last(), buffer, BAKED_MODEL.get().blockStateModel(), 1, 1, 1, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
	}
}
