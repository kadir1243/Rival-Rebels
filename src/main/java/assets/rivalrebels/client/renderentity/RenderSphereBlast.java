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

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntitySphereBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.level.lighting.LightEngine;

@Environment(EnvType.CLIENT)
public class RenderSphereBlast extends EntityRenderer<EntitySphereBlast> {
	public RenderSphereBlast(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntitySphereBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        double elev = ((Mth.sin(entity.tickCount / 40f) + 1.5f) * 10);
        matrices.translate(0, elev, 0);
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
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySphereBlast entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntitySphereBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntitySphereBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
