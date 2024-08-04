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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityB2Frag;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderB2Frag extends EntityRenderer<EntityB2Frag> {
    public RenderB2Frag(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Frag entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        matrices.scale(3, 3, 3);

        if (entity.type == 0) ObjModels.renderSolid(ObjModels.b2FragSide1, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		else if (entity.type == 1) ObjModels.renderSolid(ObjModels.b2FragSide2, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityB2Frag entity) {
        return RRIdentifiers.etb2spirit;
    }
}
