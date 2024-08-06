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
import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.client.model.ModelNuclearBomb;
import assets.rivalrebels.common.entity.EntityBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderBomb extends EntityRenderer<EntityBomb> {
    public RenderBomb(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityBomb entity, float entityYaw, float partialTick, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
        matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 90.0f));
        if (entity.getDeltaMovement().x()==0&& entity.getDeltaMovement().z()==0)
        {
            if (entity.getDeltaMovement().y() == 1)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * 0.2f, 0.25f, 0.25f, 1.0f, 0.75f);
            }
            else if (entity.getDeltaMovement().y() == 0)
            {
                ModelBlastSphere.renderModel(matrices, vertexConsumers, entity.tickCount * 0.2f, 0.8f, 0.8f, 1f, 0.75f);
            }
        }
        else {
            matrices.scale(0.25f, 0.5f, 0.25f);
            ModelNuclearBomb.renderModel(matrices, vertexConsumers, RRIdentifiers.etnuke, light, true);
        }
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBomb entity) {
        return RRIdentifiers.etnuke;
    }

    @Override
    public boolean shouldRender(EntityBomb livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
