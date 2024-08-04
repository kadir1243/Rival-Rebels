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
import assets.rivalrebels.client.model.ModelRocket;
import assets.rivalrebels.common.entity.EntitySeekB83;
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
public class RenderSeeker extends EntityRenderer<EntitySeekB83> {
	public RenderSeeker(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntitySeekB83 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 90.0f));
		matrices.scale(2.0f, 2.0f, 2.0f);
		ModelRocket.render(matrices, vertexConsumers, RRIdentifiers.etrocketseek202, true, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntitySeekB83 entity) {
        return RRIdentifiers.etrocketseek202;
    }
}
