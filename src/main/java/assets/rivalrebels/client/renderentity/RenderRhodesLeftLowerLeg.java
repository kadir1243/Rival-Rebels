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

import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityRhodesLeftLowerLeg;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
public class RenderRhodesLeftLowerLeg extends RhodesPartRenderer<EntityRhodesLeftLowerLeg> {
    public RenderRhodesLeftLowerLeg(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityRhodesLeftLowerLeg entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());
        matrices.mulPose(Axis.YP.rotationDegrees(yaw));
		matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
		matrices.translate(3, 4f, 0);
		matrices.scale(-1, 1, 1);
		ObjModels.renderSolid(ObjModels.thigh, RenderRhodes.texture, matrices, vertexConsumers, entity.getColorRGBA(), light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}
}
