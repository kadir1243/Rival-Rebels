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

import assets.rivalrebels.client.model.ModelTsarBomba;
import assets.rivalrebels.common.entity.EntityTsar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RenderTsar extends EntityRenderer<EntityTsar> {
	public RenderTsar(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityTsar entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(entity.getYRot() - 90.0f, 0.0F, 1.0F, 0.0F));
		//GlStateManager.rotatef(90.0f, 1.0F, 0.0F, 0.0F);
		matrices.mulPose(new Quaternionf(entity.getXRot() - 90.0f, 0.0F, 0.0F, 1.0F));
		ModelTsarBomba.render(matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityTsar entity) {
        return null;
    }
}
