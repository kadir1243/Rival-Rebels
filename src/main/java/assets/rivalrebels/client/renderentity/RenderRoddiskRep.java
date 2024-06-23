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

import assets.rivalrebels.client.model.ModelDisk;
import assets.rivalrebels.common.entity.EntityRoddiskRep;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderRoddiskRep extends EntityRenderer<EntityRoddiskRep>
{
	private int	er	= 0;

	public RenderRoddiskRep(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityRoddiskRep entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		er += 13.46;
        matrices.pushPose();
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f + er));

		ModelDisk.render(matrices, vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE), light, OverlayTexture.NO_OVERLAY);

        matrices.popPose();
        matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityRoddiskRep entity) {
        return null;
    }
}
