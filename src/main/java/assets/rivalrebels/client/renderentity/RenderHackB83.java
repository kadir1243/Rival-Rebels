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
import assets.rivalrebels.common.entity.EntityHackB83;
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
public class RenderHackB83 extends EntityRenderer<EntityHackB83> {
    public RenderHackB83(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityHackB83 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 180));
        ObjModels.renderSolid(ObjModels.b83, RRIdentifiers.etb83, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityHackB83 entity) {
        return RRIdentifiers.etb83;
    }

    @Override
    public boolean shouldRender(EntityHackB83 livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
