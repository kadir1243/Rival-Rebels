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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityB2Spirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderB2Spirit extends EntityRenderer<EntityB2Spirit> {
    public RenderB2Spirit(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB2Spirit entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
		matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
			matrices.scale(3, 3, 3);
            ObjModels.renderSolid(ObjModels.shuttle, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		} else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
            ObjModels.renderSolid(ObjModels.tupolev, RRIdentifiers.ettupolev, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
        } else {
            matrices.scale(3, 3, 3);
            ObjModels.renderSolid(ObjModels.b2ForSpirit, RRIdentifiers.etb2spirit, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
        }
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityB2Spirit entity) {
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
            return RRIdentifiers.etb2spirit;
        } else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
            return RRIdentifiers.ettupolev;
        } else {
            return RRIdentifiers.etb2spirit;
        }
    }

    @Override
    public boolean shouldRender(EntityB2Spirit livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
