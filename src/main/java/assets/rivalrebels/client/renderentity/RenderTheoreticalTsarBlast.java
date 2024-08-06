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
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelTsarBlast;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.entity.EntityTsarBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.lighting.LightEngine;

public class RenderTheoreticalTsarBlast extends EntityRenderer<EntityTsarBlast> {
    private final ModelTsarBlast model = new ModelTsarBlast();

    public RenderTheoreticalTsarBlast(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public void render(EntityTsarBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		double radius = (((entity.getDeltaMovement().x() * 10) - 1) * ((entity.getDeltaMovement().x() * 10) - 1) * 2) + RRConfig.SERVER.getTsarBombaStrength();
        matrices.pushPose();
		if (entity.tickCount < 60) {
			double elev = entity.tickCount / 5f;
			matrices.translate(0, elev, 0);
            ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * RRConfig.CLIENT.getShroomScale(), CommonColors.WHITE);
		}
		else if (entity.tickCount < 300 && radius - RRConfig.SERVER.getTsarBombaStrength() > 9)
		{
			double elev = (entity.tickCount - 60f) / 4f;
			matrices.translate(0, elev, 0);
			matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
			matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
			matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
			matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), CommonColors.RED);
			matrices.popPose();
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
			matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
			ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
			matrices.popPose();
		}
		else
		{
			matrices.translate(0, 10 + ((entity.getDeltaMovement().x() - 0.1d) * 14.14213562), 0);
			matrices.scale(RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale(),RRConfig.CLIENT.getShroomScale());
			matrices.scale((float) (radius * 0.116f), (float) (radius * 0.065f), (float) (radius * 0.116f));
			matrices.scale(0.8f, 0.8f, 0.8f);
			model.render(matrices, vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(RRIdentifiers.etblacktsar)), light, OverlayTexture.NO_OVERLAY, tickDelta);
		}
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityTsarBlast entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityTsarBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityTsarBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
